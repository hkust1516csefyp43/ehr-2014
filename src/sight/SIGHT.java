package sight;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Stephen, Alton
 * @version 1.0
 * @date 22/05/2015
 *
 */

public class SIGHT extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        Image image = new Image(SIGHT.class.getResourceAsStream("logo.jpg"));
        Parent root = FXMLLoader.load(getClass().getResource("SIGHT.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("One-2-One Medical System");
        stage.getIcons().setAll(image);
        stage.show();

    }

    public static void main(String[] args)
    {
		/*Runtime.getRuntime().addShutdownHook
		(
			new Thread
			(
				new Runnable()
				{
					@Override
					public void run()
					{
						System.out.println("Testing");
					}
				}
			)
		);*/
		/*try {
			Runtime.getRuntime().exec("calc");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
        launch(args);
    }

}