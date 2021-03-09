package application;


import java.util.Scanner;

import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class TurtleCanvasPainter implements TurtlePainter {

 
    private final Group canvas;
    private final double width;
    private final double height;

    private double angle = 0;    
    private final Circle turtle;
    private Color color = Color.rgb(20, 20, 20);
    private boolean isPenDown = true;

    private final SequentialTransition animation = new SequentialTransition();
    private long animationDurationMs = 100;

    public TurtleCanvasPainter(Group canvas) {
        this.canvas = canvas;
        this.width = 680 ;
        this.height = 680 ;
        this.turtle = new Circle(0 , this.height , 5, color);
        this.paintTurtle(0, 0);
        this.animation.setCycleCount(1);//powtarzanie animacji
    }

    @Override
    public void forward(int points) {
       
            final double radian = this.toRadian(this.angle);
            final double x = this.turtle.getCenterX() + points * Math.cos(radian); //cos bo x 
            final double y = this.turtle.getCenterY() - points * Math.sin(radian); //sin bo y
            this.validateBounds(x, y);
            this.moveTurtle(x, y);
      
    }
// ruszanie zolwiem i rysowanie lini, oraz zapis do animacji
    private void moveTurtle(final double x, final double y) {
     

            final Path path = new Path();
            path.getElements().add(new MoveTo(this.turtle.getCenterX(), this.turtle.getCenterY()));
            path.getElements().add(new LineTo(x, y));
            // tworznie sciezki animacji
            final PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(this.animationDurationMs));
            pathTransition.setPath(path);
            pathTransition.setNode(this.turtle);

            if (this.isPenDown) {
                final Line line = new Line(this.turtle.getCenterX(), this.turtle.getCenterY(), x, y);
                line.setStrokeWidth(1);
                line.setStroke(color);
               this.canvas.getChildren().add(line);
            }

            animation.getChildren().add(pathTransition);
            this.paintTurtle(x, y);
       
    }

    @Override
    public void back(final int points) {
       
            final double radian = this.toRadian(this.angle);
            final double x = this.turtle.getCenterX() - points * Math.cos(radian);
            final double y = this.turtle.getCenterY() + points * Math.sin(radian);
            
            this.validateBounds(x, y);
            this.moveTurtle(x, y);
       
    }

    @Override
    public void right(final int degrees) {
        this.angle = (this.angle - degrees) % 360;
       
    }

    @Override
    public void left(final int degrees) {
        this.angle = (this.angle + degrees) % 360;
    }

    @Override
    public void set(final int x, final int y) {
      
            final boolean wasPenDown = this.isPenDown;
            if (this.isPenDown) {
                this.penUp();
            }
            this.moveTurtle(x, y);
            if (wasPenDown) {
                this.penDown();
            }
        
    }

    @Override
    public void penUp() {
         this.isPenDown = false;
    }

    @Override
    public void penDown() {
        this.isPenDown = true;
    }

    @Override
    public void clear() {
       
            this.animation.getChildren().clear();
            this.canvas.getChildren().clear();
            this.paintTurtle(0 , 0 );
    }

  
	@Override
    public void setAngle(double degrees) {
		//normalnie idzie w góre, teraz w prawo dla 0.0
        this.angle = degrees - 90;
       
    }
    @Override
    public void resetAngle() {
        this.angle = 0;
    }
//włączenie animacji 
    @Override
    public void finish() {
    	this.animation.play();
      
    }

    private void validateBounds(final double newX, final double newY) {
        if ((newX < 0 || newX > this.width) || (newY < 0 || newY > this.height)) {
            throw new IllegalArgumentException(
                String.format("Żółw jest poza planszą. Wymiary planszy =(%f, %f), Pozycja żółwia=(%f, %f), Nowa pozycja=(%f, %f)",
                    this.width, this.height, this.turtle.getCenterX(), this.turtle.getCenterY(), newX, newY));
        }
    }
    
    private void paintTurtle(final double x, final double y) {
      
            this.turtle.setCenterX(x);
            this.turtle.setCenterY(y);

            this.canvas.getChildren().remove(this.turtle);
            this.canvas.getChildren().add(this.turtle);
       
    }

    private double toRadian(final double direction) {
        return direction * Math.PI / 180;
    }

    public void setAnimationDurationMs(final long animationDurationMs) {
        this.animationDurationMs = animationDurationMs;
    }
    public void setTurtleColor(int r,int g,int b) {
    	this.color =  Color.rgb(r, g, b);
    	this.turtle.setFill(color);
    }
    public void commendsInterpretation(String[] values) {
    	 

   

    	if (values[0].contains ("naprzod")) {
			forward(Integer.parseInt(values[1]));
		} else if (values[0].contains ("dotyłu")) {
			back(Integer.parseInt(values[1]));
		} else if (values[0].contains ("obrot")) {
			left(Integer.parseInt(values[1]));
		} else if (values[0].contains ("kolor")) {
			setTurtleColor(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]));
		} else if (values[0].contains ("opusc")) {
			penDown();
		}else if (values[0].contains ("podnies")) {
			penUp();
		}else if (values[0].contains ("ustaw")) {
			set(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
			setAngle(Integer.parseInt(values[4]));
		}
    }
    public String[] textDecoder(String input) {
    	
    	  //zamiana znakow ] [ ,  ; na spacje aby mógł odczytać 
    	Scanner scan = new Scanner(input.replaceAll("[,;.]", " ").replaceAll("[\\[\\].]", ""));
    
    	String line = scan.nextLine();
   
		String[] inValues = line.split(" ");
		String[] values = new String[inValues.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = (inValues[i]);
			
		}
		
		return values;
    }
}
