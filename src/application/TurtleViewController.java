package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TurtleViewController {
	private Stage courseStage;
	@FXML
	private TextArea codeEditor;

	@FXML
	private TextField codeInput;

	@FXML
	private Button btnRunAll;

	@FXML
	private Button btnRunOneLine;

	@FXML
	private Button btnRunOne;

	@FXML
	private Group logoCanvas;
	@FXML
	private Label labelLineNumber;
	@FXML
	private TextArea txtAreaLabelNumber;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnClear;

	public void setCourseStage(Stage courseStage) {
		this.courseStage = courseStage;
	}

	private TurtleCanvasPainter painter;

	public void initialize() {

		this.painter = new TurtleCanvasPainter(this.logoCanvas);

		this.painter.setAnimationDurationMs(100);
		// obrot w prawo
		this.painter.right(90);

	}

	@FXML
	public void runProgram(ActionEvent event) throws InterruptedException {
		this.painter.finish();

	}

	@FXML
	public void runAll(ActionEvent event) throws InterruptedException {
		// dzielenie stringa z codeEditor po nowej linii
		if (codeEditor.getText().equals("")) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			String content = "Załaduh komendy z pliku!";
			alert.setContentText(content);
			alert.showAndWait();
		} else {
			for (String line : codeEditor.getText().split("\\n")) {

				painter.commendsInterpretation(painter.textDecoder(line));
			}
		}
	}

	String[] temp;
	int i = 0;
	int p = 0;

	@FXML
	public void runOneLine(ActionEvent event) throws InterruptedException {
		if (codeEditor.getText().equals("")) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			String content = "Załaduh komendy z pliku!";
			alert.setContentText(content);
			alert.showAndWait();
		} else {
			painter.commendsInterpretation(painter.textDecoder(temp[p]));
			p++;
			labelLineNumber.setText(String.valueOf(p));
			if (p == temp.length) {
				p = 0;
			}
		}
	}

	@FXML
	public void runOneLineBack(ActionEvent event) throws InterruptedException {

		p--;
		labelLineNumber.setText(String.valueOf(p));
		if (p <= 0) {
			p = 0;
		}
	}

	@FXML
	public void onAbout() {
		final Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("");
		alert.setContentText("Wykonał: Przemysław Rzempołuch" + "\n" + "Komendy" + "" + "\n"

				+ "naprzod" + " przyklad uzycia naprzod 150;" + "\n" + "dotyłu" + " przyklad uzycia dotyłu 150;" + "\n"
				+ "obrot" + " przyklad uzycia obrot 120;" + "\n" + "kolor" + " przyklad uzycia kolor 250,0,0;" + "\n"
				+ "opusc" + " przyklad uzycia opusc;" + "\n" + "podnies" + " przyklad uzycia podnies;" + "\n" + "ustaw"
				+ " przyklad uzycia ustaw [15,15], 0;");

		alert.showAndWait();
	}

	@FXML
	public void onOpen() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Wybierz plik programu żółwia");

		final File file = fileChooser.showOpenDialog(null);
		if (Objects.nonNull(file)) {
			try (final BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
				final String turtleProgram = fileReader.lines().collect(Collectors.joining("\n"));
				this.codeEditor.setText(turtleProgram);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		
		// dzielenie stringa z codeEditor po nowej linii
		// stworzenie array temp
		// przypisanie do zmienne i długosci linijek
		for (String line : codeEditor.getText().split("\\n")) {

			i++;
		} // przypisanie do array temp wartosci
		temp = new String[i];

		int i = 0;
		txtAreaLabelNumber.setText("");
		for (String line : codeEditor.getText().split("\n")) {

			temp[i] = line;

			txtAreaLabelNumber.appendText("" + i + "\n");
			i++;
		}

		p = 0;
	}

	@FXML
	public void onInput() {
		// odczyt tekstu w polu tekstowy

		painter.commendsInterpretation(painter.textDecoder(codeInput.getText()));

	}
	@FXML
	public void onClear() {
		// odczyt tekstu w polu tekstowy

		painter.clear();

	}
}
