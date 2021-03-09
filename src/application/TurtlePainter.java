package application;


public interface TurtlePainter {

    void forward(final int points);

    void back(final int points);

    void right(final int degrees);

    void left(final int degrees);

    void set(final int x, final int y);

    void penUp();

    void penDown();

    void clear();

   
    void resetAngle();

    void finish();

	void setAngle(double degrees);
}
