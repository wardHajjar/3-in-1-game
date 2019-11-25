package com.example.dungeonescape.maze;

class MazeManagerEngineer {
    private MMBuilder mazeBuilder;

    public MazeManagerEngineer(MMBuilder mazeBuilder){
        this.mazeBuilder = mazeBuilder;
    }

    public MazeManager getManager(){
        return this.mazeBuilder.getManager();
    }

    public void makeManager(){
        this.mazeBuilder.setColumns();
        this.mazeBuilder.setRows();
    }

}
