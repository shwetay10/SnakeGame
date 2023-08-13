import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakeGame {
    boolean rightDirection=true;
    boolean leftDirection=false;
    boolean upDirection=false;
    boolean downDirection=false;
    boolean gameOver=false;
    Mydrawpanel canvas;
    public int[] x=new int[500];
    public int[] y=new int[500];
    public int size=3;

    public int food_x=((int)(Math.random()*48))*10;
    public int food_y=((int)(Math.random()*46))*10;
    public static void main(String[] args) {
        SnakeGame obj=new SnakeGame();
        obj.go();
    }
    public void go()
    {
        x[0]=40;
        y[0]=20;

        for(int i=1;i<size;i++)
        {
            x[i]=x[0]-i*10;
            y[i]=y[i-1];

        }
        JFrame frame=new JFrame();
        frame.setSize(498,500);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas=new Mydrawpanel();
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        canvas.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),"moveDown");
        canvas.getActionMap().put("moveDown",new myAction(4));
        canvas.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp");
        canvas.getActionMap().put("moveUp",new myAction(3));
        canvas.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        canvas.getActionMap().put("moveRight", new myAction(2));
        canvas.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        canvas.getActionMap().put("moveLeft",new myAction(1));
        while(!gameOver)
        {
            try
            {
                Thread.sleep(70);
                if(!checkCollision()){
                    foodEaten();
                    canvas.repaint();
                    new Mydrawpanel().animate();}
            }catch(Exception e){};
        }
        canvas.repaint();
    }
    public boolean checkCollision()
    {
        if(x[0]<0 || y[0]<0 || x[0]>470 || y[0]>450)
        {
            gameOver=true;
            return true;
        }
        for(int i=1;i<size;i++)
        {
            if(x[0]==x[i] && y[0]==y[i])
            {
                gameOver=true;
                return true;
            }
        }
        return false;
    }

    public void foodEaten()
    {
        if(x[0]==food_x && y[0]==food_y)
        {
            food_x=((int)(Math.random()*48))*10;
            food_y=((int)(Math.random()*46))*10;
            size++;

        }
    }
    class myAction extends AbstractAction
    {
        int p;
        private myAction(int i)
        {
            p=i;
        }
        public void actionPerformed(ActionEvent e)
        {
            if(p==4 && upDirection==false)
            {
                downDirection=true;
                rightDirection=false;
                leftDirection=false;
            }
            if(p==3 && downDirection==false)
            {
                upDirection=true;
                rightDirection=false;
                leftDirection=false;
            }
            if(p==2 && leftDirection==false)
            {
                rightDirection=true;
                downDirection=false;
                upDirection=false;
            }
            if(p==1 && rightDirection==false)
            {
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
        }
    };
    private class Mydrawpanel extends JPanel
    {

        public void paintComponent(Graphics g)
        {
            g.setColor(Color.black);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            g.setColor(Color.white);
            if(gameOver)
                g.drawString("GAME OVER", 210, 220);

            g.fillOval(x[0], y[0], 10, 10);
            for(int i=size-1;i>0;i--)
            {

                g.fillOval(x[i], y[i], 10, 10);
            }
            g.setColor(Color.red);
            g.fillOval(food_x, food_y, 10, 10);
        }
        public void animate()
        {
            for(int i=size-1;i>0;i--)
            {
                x[i]=x[i-1];
                y[i]=y[i-1];
            }
            if(rightDirection==true)
                x[0]+=10;
            if(leftDirection==true)
                x[0]-=10;
            if(upDirection==true)
                y[0]-=10;
            if(downDirection==true)
                y[0]+=10;
        }
    }

}