package application;

//Standard javafx imports.
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;

//Components in this application.
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

//Support for quitting.
import javafx.application.Platform;

//Menus
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

//Layout imports.
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

//Imports for images.
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Imports for file handling.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.io.File;
import java.io.FileOutputStream;

//Print imports.
import javafx.print.PageLayout;
import javafx.print.PrinterJob;

public class FXNotes extends Application {
	// Declare components that need class scope.
	MenuBar mBar;

	// Declare menu File components.
	Menu mnuFile;
	MenuItem miFileNew, miFileOpen, miFileClose, miFileSave, miFileSaveAs, miFilePrint, miFileExit;

	// Declare menu Edit components.
	Menu mnuEdit;
	MenuItem miEditDelete, miEditCut, miEditCopy, miEditPaste, miEditSelectAll;

	// Declare menu Help components.
	Menu mnuHelp;
	MenuItem miHelpAbout;

	// Declare the main text area.
	TextArea txtMain;

	// Store file path.
	File file;

	// Store primary stage path.
	Stage pStage;

	public FXNotes() {
		// Instantiate components with 'new' keyword.
		mBar = new MenuBar();

		// Menu File
		mnuFile = new Menu("File");
		miFileNew = new MenuItem("New");
		miFileOpen = new MenuItem("Open");
		miFileClose = new MenuItem("Close");
		miFileSave = new MenuItem("Save");
		miFileSaveAs = new MenuItem("Save as...");
		miFilePrint = new MenuItem("Print");
		miFileExit = new MenuItem("Exit");

		// Menu Edit
		mnuEdit = new Menu("Edit");
		miEditDelete = new MenuItem("Delete");
		miEditCut = new MenuItem("Cut");
		miEditCopy = new MenuItem("Copy");
		miEditPaste = new MenuItem("Paste");
		miEditSelectAll = new MenuItem("Select All");

		// Menu Help
		mnuHelp = new Menu("Help");
		miHelpAbout = new MenuItem("About");

		// The main text area.
		txtMain = new TextArea();

	}// constructor()

	@Override
	public void init() {
		// Event handling mainly.

		// Menu file.
		miFileNew.setOnAction(ae -> doFileNew());
		miFileOpen.setOnAction(ae -> doFileOpen());
		miFileClose.setOnAction(ae -> doFileClose());
		miFileSave.setOnAction(ae -> doFileSave(file));
		miFileSaveAs.setOnAction(ae -> doFileSaveAs());
		miFilePrint.setOnAction(ae -> doFilePrint());
		miFileExit.setOnAction(ae -> doFileExit());

		// Menu Edit.
		miEditDelete.setOnAction(ae -> doEditDelete());
		miEditCut.setOnAction(ae -> doEditCut());
		miEditCopy.setOnAction(ae -> doEditCopy());
		miEditPaste.setOnAction(ae -> doEditPaste());
		miEditSelectAll.setOnAction(ae -> doEditSelectAll());

		// Menu Help.
		miHelpAbout.setOnAction(ae -> doHelpAbout());

	}// init()

	/** Create a new File. (Menu File) */
	public void doFileNew() {

		// if there is a opened file or if there is text in textMain.
		if (file != null || !(txtMain.getText().equals(""))) {

			// invoke method to display warn save dialog.
			int result = warnSaveDialog();

			if (result == 1) { // Save selected.
				doFileSaveAs();
				closeAction();
			} else if (result == 0) {// Don't save selected
				closeAction(); // Close file.
			} else
				; // Cancel selected, do nothing.
		} else
			;// No file opened and nothing to save, do nothing.
	} // doFileNew()

	/** Open a File. (Menu File) */
	public void doFileOpen() {
		// if there is a opened file or if there is text in txtMain.
		if (file != null || !(txtMain.getText().equals(""))) {

			// invoke method to display warn save dialog.
			int result = warnSaveDialog();

			if (result == 1) { // Save selected.
				doFileSave(file);
				openFileDialog();
			} else if (result == 0) { // Don't save selected.
				closeAction();
				openFileDialog();
			} else // Cancel selected, do nothing.
				;
		} else // No file opened and txtMain empty.
			openFileDialog();
	} // doFileOpen()

	/** Open file dialog */
	public void openFileDialog() {

		// Create a file chooser to allow selection of a file.
		FileChooser fc = new FileChooser();
		fc.setTitle("Select an Text File:");

		// Set the extension filter to choose only text files.
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

		// Assign a file.
		File fileToOpen = fc.showOpenDialog(null);

		// Try to open the file.
		openFileAction(fileToOpen);

	} // openFileDialog()

	/** Try to open the file */
	public void openFileAction(File fileToOpen) {
		// If the dialog is confirmed (OK is clicked).
		if (fileToOpen != null) {
			// Try to open the file and display in the main text area.
			try {
				// Use a string builder to accumulate lines from the file.
				StringBuilder sb = new StringBuilder();

				// Use a buffered reader to read from the file.
				FileReader fr = new FileReader(fileToOpen);
				BufferedReader buf = new BufferedReader(fr);

				// A string to store lines from the file temporarily.
				String text;

				// Iterate through the file reading one line at a time.
				while ((text = buf.readLine()) != null) {
					text = text + "\n";

					// Append the line of text to the string builer's accumulated text.
					sb.append(text);
				}
				// Done iterating trough the file. EOF reached. Add the entire text to txtMain.
				txtMain.setText(sb.toString());

				// Close buffered reader.
				buf.close();

				// Store file Path
				file = fileToOpen;

				// Update title
				updateTitle();
			} catch (IOException ioe) {
				System.out.print("Error opening file: ");
				ioe.printStackTrace();
				System.out.println();
			}
		}
	} // openFileAction()

	/** Close file. (Menu File) */
	public void doFileClose() {
		// If not (file not and txtMain empty.
		if (!(file == null && txtMain.getText().equals(""))) {
			if (file == null) {
				// Warn user to save.
				int result = warnSaveDialog();
				if (result == 1) { // Save selected.
					doFileSaveAs();
					closeAction(); // Dont't save selected.
				} else if (result == 0) {
					closeAction();
				} else
					; // Cancel selected, do nothing.
			} else { // File opened.
				// Warn user to save.
				int result = warnSaveDialog();
				if (result == 1) { // Save selected.
					doFileSave(file);
					closeAction(); // Dont't save selected.
				} else if (result == 0) {
					closeAction();
				} else
					; // Cancel selected, do nothing.
			}
		} else
			; // No file opened and txt main empty, do nothing.
	} // doFileClose()

	/** Close file */
	public void closeAction() {
		file = null;
		txtMain.setText("");
		updateTitle();
	} // closeAction()

	/** Save a file. (Menu File). */
	public void doFileSave(File fileToSave) {
		// Check if file exists.
		if (fileToSave != null) {
			// Try to save the file using the name given.
			try {
				// Overwrite file.
				FileOutputStream fos = new FileOutputStream(fileToSave, false);

				// Store text in txtMain in bytes
				byte[] dataOut = txtMain.getText().getBytes();

				// Write dataOut to the file.
				fos.write(dataOut);

				// Flush the fos to the file. It might just be buffered.
				fos.flush();

				// Close the fos.
				fos.close();

				// Open saved file.
				openFileAction(fileToSave);
			} catch (IOException ioe) {
				System.out.println("Error saving file:\n");
				ioe.printStackTrace();
			}
		} else { // Open Save Dialog.
			doFileSaveAs();
		}
	} // doFileSave()

	/** Save file with dialog (Menu File). */
	public void doFileSaveAs() {

		// Use a file chooser.
		FileChooser fc = new FileChooser();

		// Set the extension filter to choose only text files.
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

		// Show the file save dialog{
		File fileToSave = fc.showSaveDialog(null);

		// If the dialog is confirmed(Ok clicked). Save the file.
		if (fileToSave != null) {
			doFileSave(fileToSave);
		} else
			; // no file selected, do nothing

	}// doFileSaveAs()

	/** Show warn save dialog */
	public int warnSaveDialog() {
		// Use alert to warn user to save.
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("FXNotes");
		alert.setHeaderText("Do you want to save your document?");

		// Create save buttons
		ButtonType btSave = new ButtonType("Save");
		ButtonType btDSave = new ButtonType("Don't Save");

		// Create a button of type cancel
		ButtonType btCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		// Add buttons to alert.
		alert.getButtonTypes().setAll(btSave, btDSave, btCancel);

		// Use ButtonType to store clicked selected button result.
		Optional<ButtonType> result = alert.showAndWait();

		// Return selected button.
		if (result.get() == btSave) {
			return 1; // S
		} else if (result.get() == btDSave) {
			return 0; // Don't save selected.
		} else
			return -1; // Cancel selected.
	} // warnSaveDialog()

	/** Print file. (Menu File) */
	public void doFilePrint() {

		// Create a TextFlow with txtMain content.
		TextFlow printArea = new TextFlow(new Text(txtMain.getText()));

		// Create a printerJob.
		PrinterJob printerJob = PrinterJob.createPrinterJob();

		// If printerJob is valid and Ok button is clicked in print dialog.
		if (printerJob != null && printerJob.showPrintDialog(txtMain.getScene().getWindow())) {
			// Create a PageLayout with job settings in dialog.
			PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();
			printArea.setMaxWidth(pageLayout.getPrintableWidth());

			// Try to print PrintArea
			if (printerJob.printPage(printArea))
				printerJob.endJob(); // done printing

			else // Print failed.
				System.out.println("Failed to print");

		} else
			;// Canceled, do nothing; {
	}// doFilePrint()

	/** Exit program. (Menu File) */
	public void doFileExit() {
		// If file is null and txtMain is empty, exit the program.
		if (file == null && txtMain.getText().equals("")) {
			Platform.exit();
		} else { // Warn user to save.
			// Use alert.
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("FXNotes");
			alert.setHeaderText("Do you want to save your document?");

			// Create Buttons
			ButtonType btSave = new ButtonType("Save");
			ButtonType btDSave = new ButtonType("Don't Save");
			ButtonType btCancel = new ButtonType("Cancel");

			// Add buttons to alert.
			alert.getButtonTypes().setAll(btSave, btDSave, btCancel);

			// Use ButtonType to store result.
			Optional<ButtonType> result = alert.showAndWait();

			// Save work if required.
			if (result.get() == btSave) {
				doFileSave(file); // Save work.

			} else if (result.get() == btDSave) {
				Platform.exit(); // Exit without saving.

			} else
				; // Cancel selected, do nothing.
		} // endif
	} // doFileExit()

	/** Delete selected text. (Menu Edit) */
	public void doEditDelete() {
		txtMain.deleteText(txtMain.getSelection());
	}

	/** Cut selected text. (Menu Edit) */
	public void doEditCut() {
		txtMain.cut();
	}

	/** Copy selected text. (Menu Edit) */
	public void doEditCopy() {
		txtMain.copy();
	}

	/** Paste clipboard content. (Menu Edit) */
	public void doEditPaste() {
		txtMain.paste();
	}

	/** Select all text. (Menu Edit) */
	public void doEditSelectAll() {
		txtMain.selectAll();
	}

	/** Show About dialog. (Menu Help) */
	public void doHelpAbout() {
		// Use an alert for show about dialog.
		Alert alert = new Alert(AlertType.INFORMATION);

		alert.setTitle("About FXNotes");
		alert.setHeaderText("FXNotes V.1.0.0");
		alert.setContentText("Student info:\n" + "Jonatan Freitas");

		// Set a custom image into the alert.
		// Remember to do the Image and ImageView imports.
		Image img = new Image("logo.png");
		ImageView imView = new ImageView(img);

		// Resize image.
		imView.setFitWidth(100);
		imView.setFitHeight(100);

		alert.setGraphic(imView);

		// On Linux platforms, this stops the alert from showing only its top LH corner.
		alert.setResizable(true);

		alert.showAndWait();
	}

	/** Update stage title */
	public void updateTitle() {
		if (file == null) {
			pStage.setTitle("FXNotes");
		} else {
			pStage.setTitle(file.getName() + " - FXNotes");
		}
	}

	@Override /** Layout/GUI construction */
	public void start(Stage primaryStage) {

		// Set the title
		primaryStage.setTitle("FXNotes");

		// Set icon for the application.
		primaryStage.getIcons().add(new Image("logo.png"));

		// Set the width and height
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);

		// Create a layout.
		BorderPane bpMain = new BorderPane();

		// Set Text to wrap according to txtMain width.
		txtMain.setWrapText(true); //

		// Add components to the layout.
		bpMain.setCenter(txtMain);
		bpMain.setTop(mBar);

		// Add menus to the menu bar.
		mBar.getMenus().add(mnuFile);
		mBar.getMenus().add(mnuEdit);
		mBar.getMenus().add(mnuHelp);

		// Add menu items to the File menu.
		mnuFile.getItems().add(miFileNew);
		mnuFile.getItems().add(miFileOpen);
		mnuFile.getItems().add(miFileClose);
		mnuFile.getItems().add(miFileSave);
		mnuFile.getItems().add(miFileSaveAs);
		mnuFile.getItems().add(miFilePrint);
		mnuFile.getItems().add(miFileExit);

		// Add menu items to the Edit menu.
		mnuEdit.getItems().add(miEditDelete);
		mnuEdit.getItems().add(miEditCut);
		mnuEdit.getItems().add(miEditCopy);
		mnuEdit.getItems().add(miEditPaste);
		mnuEdit.getItems().add(miEditSelectAll);

		// Add menu items to the Help menu.
		mnuHelp.getItems().add(miHelpAbout);

		// Create a scene
		Scene s = new Scene(bpMain);

		// Set the scene
		primaryStage.setScene(s);

		// Show the stage
		primaryStage.show();

		pStage = primaryStage;

	}// start()

	public static void main(String[] args) {
		// Launch the Application
		launch(args);
	}// main()
}// class
