package application;
	
import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	
	public void populateFiles(File dir, GridPane gp) {
		String[] fileNames = dir.list(); 
		gp.setHgap(10);
		Label col1 = new Label("Filename   ");
		Label col2 = new Label("Group Num");
		
		GridPane.setConstraints(col1, 0, 0);
		GridPane.setConstraints(col2, 1, 0);
		gp.getChildren().add(col1);
		gp.getChildren().add(col2);

		for (int i=0; i<fileNames.length; i++) {
			Text fn = new Text(fileNames[i]);
			System.out.println("File: "+fileNames[i]);
			TextField tf = new TextField();
			tf.setMaxWidth(40);
			GridPane.setConstraints(fn, 0, i+1);
			GridPane.setConstraints(tf, 1, i+1);
			gp.getChildren().add(fn);
			gp.getChildren().add(tf);
		}
		// gp.getChildren().addAll(fn2, fn3, fn4, fn5, fn6, tf2, tf3, tf4, tf5, tf6);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Text errorMsg = new Text("");
			errorMsg.setFont(new Font("Helvetica", 20));
			errorMsg.setFill(Color.RED);
			String initDir="/home/cahall/";
			BorderPane bp = new BorderPane();
			GridPane gp = new GridPane();
			HBox hb = new HBox();
			Label raceLabel = new Label("Race ID:");
			TextField raceId = new TextField();
			raceId.setMaxWidth(50);
			hb.getChildren().addAll(raceLabel, raceId);
			hb.setAlignment(Pos.CENTER);
			// http://tutorials.jenkov.com/javafx/filechooser.html
			// FileChooser fc = new FileChooser();
			// https://stackoverflow.com/questions/9375938/javafx-filechooser
			DirectoryChooser dc = new DirectoryChooser();
			dc.setInitialDirectory(new File(initDir));
			Button fcbtn = new Button("Choose Directory");
			Text dirText = new Text("Directory: "+initDir);
			// fc.showOpenDialog
			// fc.showOpenMultipleDialog
			// DirectoryChooser chooser = new DirectoryChooser();
			
			fcbtn.setOnAction(e -> { 
				File newDir = dc.showDialog(primaryStage); 
				String dirName = newDir.getPath();
				dirText.setText(dirName);
				populateFiles(newDir, gp);
			});
			// gp.setGridLinesVisible(true);
			VBox vb = new VBox();
			Text ubuf = new Text("      ");
			Text title = new Text("MARRS File Rename Utility");
			
			title.setFont(new Font("Helvetica", 32));
			dirText.setFont(new Font("Helvetica", 20));
			vb.getChildren().addAll(ubuf, title, hb, dirText, fcbtn);
			// https://stackoverflow.com/questions/35159841/javafx-centering-vbox-inside-gridpane
			vb.setAlignment(Pos.CENTER);
			gp.setAlignment(Pos.CENTER);
			VBox bvb = new VBox();  // bottom VBox
			bvb.getChildren().add(new Text("   "));
			bvb.getChildren().add(errorMsg);
			bvb.setAlignment(Pos.CENTER);
			Button renameBtn = new Button("Rename the Files");
			renameBtn.setOnAction(e -> { 
				String path=dirText.getText();
				System.out.println("Directory: "+path);
				// get the raceID
				String rid = raceId.getText();
				System.out.println("Race ID: x"+rid+"x");
				if (rid == null || rid.equals("")) {
					errorMsg.setText("Please enter a Race ID");
				} else {
				// traverse the GridPane and print out the file name for now...
				List<Node> gpl = gp.getChildren();
				for (int i=0; i<gpl.size(); i=i+2) {
					Node x = gpl.get(i);
					// String nm = x.getClass().getName();
					String sn = x.getClass().getSimpleName();
					// System.out.println("Rename: got a node! "+i+" "+sn);
					if (sn.equals("Label")) continue;
					if (sn.equals("Text")) {
						Text textNode = (Text) x;
						String fn = textNode.getText();
						Node x2=gpl.get(i+1);
						String tfsn = x2.getClass().getSimpleName();
						if (!tfsn.equals("TextField")) {
							System.out.println("filename: "+fn+" Unexpected TextField Node Type: "+tfsn);
							continue;
						}
						TextField tf = (TextField) x2;
						String val = tf.getText();
						String newFileName = "Race-"+rid+"-Group-"+val+"-results.csv";
						if (val == null || val.equals("")) {
							Text err = new Text("Skipping; "+fn);
							bvb.getChildren().add(err);
							continue;
						}
						File oldFile = new File(path+File.separator+fn);
						File newFile = new File(path+File.separator+newFileName);
						Boolean success = oldFile.renameTo(newFile);
						System.out.println("Success: "+success+" Rename from: "+fn+" to: "+newFileName);
					}
				}
				}
			});
			bvb.getChildren().add(renameBtn);
			bp.setTop(vb);
			bp.setCenter(gp);

			bp.setBottom(bvb);
			BorderPane.setMargin(bp.getBottom(), new Insets(10, 10, 160, 10)); // top, right, bottom, left margins
			// BorderPane.setAlignment(bp.getTop(), Pos.CENTER); // does not work now that top is a Vbox
			BorderPane.setAlignment(bp.getBottom(), Pos.CENTER);
			
			BorderPane.setAlignment(bp.getCenter(), Pos.CENTER);
			Scene scene = new Scene(bp,1200,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("MARRS File Rename Utility");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
