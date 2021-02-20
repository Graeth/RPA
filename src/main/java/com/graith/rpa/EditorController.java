package com.graith.rpa;

import java.io.IOException;
import java.util.List;

import com.graith.rpa.command.ActionLoader;
import com.graith.rpa.graph.ActionNode;
import com.graith.rpa.process.RPAProcess;
import com.graith.rpa.property.StringProperty;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class EditorController {

	private Thread process;
	
	@FXML
	private Button titleButton, executeButton, newProgramButton, openProgramButton;
	
	@FXML
	private TabPane programTabPane;
	
	@FXML
	private TreeTableView<DisplayableProperty> tableView;
	

	private TreeTableColumn<DisplayableProperty, String> valueColumn, fieldColumn;
	
	private List<DisplayableObject> dObjects;
	
	@FXML
	private TitledPane actionPane, criterionPane, routinePane;
	//http://tutorials.jenkov.com/javafx/treetableview.html
	
    @FXML
    private void switchToTitle() throws IOException {
        App.setRoot("title");
    }
    
    @FXML
    private void execute() throws IOException {
    	if(process == null) {
        	process = new Thread() {
        		public void run() {
        			new RPAProcess();
        		}
        	};
        	process.setDaemon(true);
        	process.start();
        }
    }
    
    @FXML
    private void createNewProgram() throws IOException {
    	
    }
    
    @FXML
    private void openProgram() throws IOException {
    	
    }
    
    private void initializeProgramTab() {
    	
    }
    
    private void updateTreeTable(ActionNode an) {
    	//DisplayableObject dObj = new DisplayableObject(an);
    	List<DisplayableProperty> svu = an.getCommand().getFields();
    	for(DisplayableProperty svuU : svu) {
    		System.out.println(svuU.getLabel() + " " + svuU.getValue());
    	}
    	System.out.println(an.getId());
    	TreeItem<DisplayableProperty> itList = new TreeItem<DisplayableProperty>((new StringProperty(an.getId(), "...")));
    	System.out.println("test 2");
    	for(DisplayableProperty<?> svuSingle : svu) {
    		TreeItem<DisplayableProperty> item = new TreeItem<>(svuSingle);
    		itList.getChildren().add(item);
    		System.out.println("test 3");
    	}
    	System.out.println("test =");
    	tableView.setRoot(itList);
    }
    
    private void addActionNode(ActionNode an) {
    	updateTreeTable(an);
    	System.out.println(an.toString());
    }
    
    private Node getDefaultActionContent() {
    	Button mouseClickButton = new Button("MouseClick");
    	mouseClickButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	        addActionNode(ActionLoader.genericClickNode());
    	    }
    	});
    	Group group = new Group();
    	group.getChildren().add(mouseClickButton);
    	return group;
    }
    
    private void initializeAccordion() {
    	actionPane.setContent(getDefaultActionContent());
    }
    
    private void initializeTable() {
    	if(valueColumn == null) {
	    	valueColumn = new TreeTableColumn<>("Value");
	    	fieldColumn = new TreeTableColumn<>("Label");
	    	fieldColumn.setPrefWidth(150);
	    	tableView.getColumns().add(fieldColumn);
	    	tableView.getColumns().add(valueColumn);
	    	valueColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("stringValue"));
	    	fieldColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("label"));
    	}
    }
    
    @FXML
    public void initialize() {
    	initializeAccordion();
    	initializeProgramTab();
    	initializeTable();
    }
}