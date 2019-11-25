package com.example.dungeonescape.maze;

class EasyMazeManagerBuilder implements MMBuilder {
        private MazeManager manager;

        public EasyMazeManagerBuilder(){
            manager = new MazeManager();
        }

        public void setColumns(){manager.setNumMazeCols(5);}

        public void setRows(){manager.setNumMazeRows(5);}

        public MazeManager getManager(){return manager;}

}
