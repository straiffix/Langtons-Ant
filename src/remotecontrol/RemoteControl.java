package remotecontrol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import LangtonsAnt.Ant;
import LangtonsAnt.SimulatorGlobal;
import LangtonsAnt.Main;
import LangtonsAnt.Console;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class RemoteControl {
    Socket socket;
    ServerSocket serverSocket;
    boolean isClientSocketOpened = false;
    boolean isServerSocketOpened = false;
    public boolean isClient = false;
    ParametersSettings downloadedSettings;
    String address;
    int port;
    Console console;

    SerializableColor[][] grid;
    GraphicsContext gc;
    List<Ant> ants;
    public boolean isBusy =false;

    public RemoteControl(String address, int port, boolean isClient, SerializableColor[][] grid, GraphicsContext gc, List<Ant> ants, Console console) {
        this.isClient = isClient;
        this.grid = grid;
        this.gc = gc;
        this.ants = ants;
        this.address = address;
        this.port = port;
        this.console=console;

    }

    public void start() {

        if (this.isClient) {
            try {
                socket = new Socket(address, port);
                isClientSocketOpened = true;
                for (char c : "Jesteś klientem".toCharArray()){
                    console.write(c);
                }

            } catch (Exception ex) {
                isClientSocketOpened = false;
                System.out.println(ex.getMessage());
                for (char c : "Nie udało się połączyc z serwerem".toCharArray()){
                    console.write(c);
                }

            }
        } else {
            try {
                this.serverSocket = new ServerSocket(port);
                Thread thread = new Thread(()->{
                    while(isServerSocketOpened){
                        try {
                            Socket soc = serverSocket.accept();
                            synchronized (this){
                                DataInputStream dos = new DataInputStream(soc.getInputStream());
                                ObjectInputStream out = new ObjectInputStream(dos);
                                downloadedSettings = (ParametersSettings) out.readObject();
                                parseSettings(downloadedSettings);
                                out.close();
                                dos.close();
                                for (char c : "Jesteś serwerem".toCharArray()){
                                    console.write(c);
                                }

                            }

                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            for (char c : "Połączenie się nie udało".toCharArray()){
                                console.write(c);
                            }

                        }
                    }
                });
                thread.start();

                isServerSocketOpened = true;
            } catch (Exception ex) {
                isServerSocketOpened = false;
                System.out.println(ex.getMessage());
            }
        }
    }

    public void stop() {
        if (this.isClient) {
            try {
                isClientSocketOpened = false;
                socket.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            try {
                socket.close();
                serverSocket.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public void sendSettings(ParametersSettings settings) {
        Thread thread = new Thread(()-> {
                this.isBusy=true;
                this.start();
                try {
                    if (this.isClient) {
                        DataOutputStream dos = new DataOutputStream(this.socket.getOutputStream());
                        ObjectOutputStream out = new ObjectOutputStream(dos);
                        out.writeObject(settings);
                        out.close();
                        dos.flush();
                        dos.close();
                    } else {
                        throw new IllegalArgumentException("obiekt jest serwerem, więc nie może wysyłać");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                this.stop();
                this.isBusy=false;
        });
        thread.start();

    }

    public void sendSettings(ParametersSettingsType settingsType) {
        if (settingsType == ParametersSettingsType.SIMULATOR_GLOBAL_SETTINGS) {

            ParametersSettings sett = new ParametersSettings();
            sett.width = SimulatorGlobal.width.getValue();
            sett.height = SimulatorGlobal.height.getValue();
            sett.sizeOfCell = SimulatorGlobal.cellSize.getValue();
            sett.speedOfAnimation = SimulatorGlobal.simulateSpeed.getValue();
            sett.isRunning = !SimulatorGlobal.simulateStopped.getValue();
            sett.type = settingsType;
            this.sendSettings(sett);

        }
    }

    public void sendSettings(int x, int y, Color color, int direction){

        ParametersSettings sett = new ParametersSettings();
        sett.colorOfNewAnt = new SerializableColor(color);
        sett.newAntDirection= direction;
        sett.newAntX = x;
        sett.newAntY = y;
        sett.type = ParametersSettingsType.ADD_ANT;
        this.sendSettings(sett);


    }

    public void parseSettings(ParametersSettings settings) {
        if (settings.type == ParametersSettingsType.SIMULATOR_GLOBAL_SETTINGS) {
            synchronized (settings) {
                SimulatorGlobal.width.setValue(settings.width);
                SimulatorGlobal.height.setValue(settings.height);
                SimulatorGlobal.cellSize.setValue(settings.sizeOfCell);
                SimulatorGlobal.simulateSpeed.setValue(settings.speedOfAnimation);
                SimulatorGlobal.simulateStopped.setValue(!settings.isRunning);
            }
        } else if (settings.type == ParametersSettingsType.ADD_ANT) {
            synchronized (this.ants) {
                synchronized (settings) {
                    Ant ant = new Ant(settings.newAntX, settings.newAntY, settings.newAntDirection, settings.colorOfNewAnt, Main.grid, this.gc, console);
                    Main.ants.add(ant);
                    ant.setAnt();
                }
            }
        }else if(settings.type == ParametersSettingsType.RETURN_BOARD){
            synchronized(Main.grid){
                Main.grid = settings.returnBoard;
                //Board.drawBoard(Main.grid,gc,SimulatorGlobal.width.getValue(), SimulatorGlobal.height.getValue());
            }
        }
    }


    public ParametersSettings getSettings() {
        return downloadedSettings;
    }


}
