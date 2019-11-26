package com.example.dungeonescape.maze;

class HardMMBuilder implements MMBuilder {
    //the MazeManager object which we will modify and return.
    private MazeManager manager;

    public HardMMBuilder(){
        manager = new MazeManager();
    }

    // the rows and columns are set to 20 corresponding to the chosen difficulty.
    public void setColumns(){manager.setNumMazeCols(20);}

    public void setRows(){manager.setNumMazeRows(20);}

    // return the manager when requested by engineer.
    public MazeManager getManager(){return manager;}
}
