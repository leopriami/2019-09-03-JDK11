/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.Graphs;

import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...\n");
    	Integer N = -1;
    	try {
    		N = Integer.parseInt(txtPassi.getText());
    	}
    	catch(Exception e) {
    		txtResult.appendText("inserisci un numero intero");
    	}
    	if(N<=0) {
    		txtResult.appendText("inserisci un numero naturale");
    		return;
    	}
    	String porzione = boxPorzioni.getValue();
    	if(porzione == null) {
    		txtResult.appendText("scegli una porzione");
    		return;
    	}
    	this.model.cercaOttimo(porzione, N);
    	txtResult.appendText("peso: "+this.model.getPesoOttimo()+"\n");
    	for(String s : this.model.getCamminoOttimo()) {
    		txtResult.appendText(s+"\n");
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...\n");
    	String porzione = boxPorzioni.getValue();
    	if(porzione == null) {
    		txtResult.appendText("scegli una porzione");
    		return;
    	}
    	for(String v1 : Graphs.neighborListOf(this.model.getGrafo(), porzione)) {
    		txtResult.appendText(v1+" "+this.model.getGrafo().getEdgeWeight(this.model.getGrafo().getEdge(porzione, v1))+"\n");
		}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Integer calorie = -1;
    	try {
    		calorie = Integer.parseInt(txtCalorie.getText());
    	}
    	catch(Exception e) {
    		txtResult.appendText("inserisci un numero intero");
    	}
    	if(calorie<=0) {
    		txtResult.appendText("inserisci un numero naturale");
    		return;
    	}
    	txtResult.appendText("Creazione grafo...\n");
    	this.model.creaGrafo(calorie);
    	txtResult.appendText("#nodi: "+this.model.getGrafo().vertexSet().size()+"\n");
    	txtResult.appendText("#archi: "+this.model.getGrafo().edgeSet().size()+"\n");
    	boxPorzioni.getItems().addAll(this.model.getGrafo().vertexSet());
    	btnCorrelate.setDisable(false);
    	btnCammino.setDisable(false);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	btnCorrelate.setDisable(true);
    	btnCammino.setDisable(true);
    }
}
