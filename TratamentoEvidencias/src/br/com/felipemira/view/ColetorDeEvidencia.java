package br.com.felipemira.view;

import java.io.File;
import java.io.IOException;



import br.com.felipemira.ExecutarCasoDeTeste;
import br.com.felipemira.objects.object.Error;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ColetorDeEvidencia extends Application{
	
	private Scene scene;
	private Image applicationIcon;
	
	private AnchorPane pane;
	private Label labelArquivoDOCX, labelBy, labelExecutor, labelCiclo, labelAmbiente, instrucoes1, instrucoes2, instrucoes3, instrucoes4; //labelModel
	private TextField txExecutor, txAmbiente, txArquivoDOCX;//txModel
	final String[] greetings = new String[] {"1º", "2º", "3º"};
	private String ciclo = "";
	@SuppressWarnings("rawtypes")
	private ChoiceBox cbCiclo;
	private Button btnExecutar;
	private ProgressBar progressBar;
	private ImageView teclas; 
	//imagem, teclas;
	
	//private String localIconeAplicativo = "br/com/felipemira/img/icone.gif";
	
	
	private static Stage stage;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Service<Void> servico = new Service(){
		@Override
        protected Task createTask() {
            return new Task() {
            	@Override
                protected Void call() throws NumberFormatException, InterruptedException, IOException {
            			ExecutarCasoDeTeste.executar(txExecutor.getText(), ciclo, txAmbiente.getText(), txArquivoDOCX.getText());
            		return null;
            	}
        	};
	 	}	
	};
	
	ChangeListener<Worker.State> listener = new ChangeListener<Worker.State>() {
        @Override
        public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState) {
            if (newState == Worker.State.SUCCEEDED) {
            	progressBar.progressProperty().unbind();
            	progressBar.setProgress(new Float(0f));
            	alert("Finalizado!", "Coletor de Evidências Finalizado!");
            	btnExecutar.setText("Executar");
            	
            }
            if(newState == Worker.State.FAILED){
            	progressBar.progressProperty().unbind();
            	progressBar.setProgress(new Float(0f));
            	alertError("Erro!", Error.error);
            	btnExecutar.setText("Executar");
            }
            if(newState == Worker.State.RUNNING){
            	btnExecutar.setText("Cancelar");
            }
        }
	};
	
	@Override
	public void start(Stage stage) throws Exception {
		initComponents();
		initListeners();
		
		stage.setScene(scene);
		stage.setTitle("Coletor de Evidências");
		stage.resizableProperty().set(false);
		//applicationIcon = new Image(localIconeAplicativo);
		//stage.getIcons().add(applicationIcon);
		stage.setMaxWidth(800);
		stage.setMaxHeight(330);
		stage.show();
		
		initLayout();
		ColetorDeEvidencia.stage = stage;
	}
	
	public static void main(String[] args){
		launch(args);
	}
	
	public static Stage getStage(){
		return stage;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents(){
		pane = new AnchorPane();
		pane.setPrefSize(800, 300);
		pane.setStyle("-fx-background-color:#f97a18");
		
		
		labelExecutor = new Label("Executor:");
		txExecutor = new TextField();
		txExecutor.setPromptText("Nome do Executor");
		
		labelAmbiente = new Label("Ambiente:");
		txAmbiente = new TextField();
		txAmbiente.setPromptText("Ambiente");
		
		labelCiclo = new Label("Ciclo:");
		
		cbCiclo = new ChoiceBox(FXCollections.observableArrayList("1º", "2º", "3º"));
		
		labelArquivoDOCX = new Label("Arquivo DOCX:");
		txArquivoDOCX = new TextField();
		txArquivoDOCX.setPromptText("Arquivo DOCX...");
		
		/*
		labelLinhaInicio = new Label("Linha Início:");
		txLinhaInicio = new TextField();
		txLinhaInicio.setPromptText(">=12");
		txLinhaInicio.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	txLinhaInicio.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
		
		labelLinhaFim = new Label("Linha Fim:");
		txLinhaFinal = new TextField();
		txLinhaFinal.setPromptText(">=12");
		txLinhaFinal.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	txLinhaFinal.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
	    
	    */
		
		btnExecutar = new Button("Executar");
		
		//imagem = new ImageView();
		//imagem.setImage(new Image("br/com/felipemira/img/imagem.png"));
		
		teclas = new ImageView();
		teclas.setImage(new Image("br/com/felipemira/img/teclas.png"));
		
		instrucoes1 = new Label("Tirar Print");
		
		instrucoes2 = new Label("Avançar Passo");
				
		instrucoes3 = new Label("Voltar Passo");
				
		instrucoes4 = new Label("Falhar Teste");
		
		labelBy = new Label("Criado por Felipe Mira");
		
		progressBar = new ProgressBar();
		progressBar.setProgress(new Float(0f));
		
		pane.getChildren().addAll(labelExecutor, txExecutor, labelCiclo, cbCiclo, labelAmbiente, txAmbiente, labelArquivoDOCX, txArquivoDOCX, btnExecutar, progressBar, teclas, instrucoes1, instrucoes2, instrucoes3, instrucoes4, labelBy);//, labelBy, labelModel, txModel);
		scene = new Scene(pane);
	}
	
	private void initLayout(){
		
		labelExecutor.setLayoutX(((pane.getWidth() - labelExecutor.getWidth()) / 2) - 300);
		labelExecutor.setLayoutY(20);
		txExecutor.setLayoutX(((pane.getWidth() - txExecutor.getWidth()) / 2) - 100);
		txExecutor.setPrefWidth(500);
		txExecutor.setLayoutY(20);
		
		labelCiclo.setLayoutX(((pane.getWidth() - labelCiclo.getWidth()) / 2) - 288);
		labelCiclo.setLayoutY(60);
		cbCiclo.setLayoutX(((pane.getWidth() - cbCiclo.getWidth()) / 2) - 156);
		cbCiclo.setPrefWidth(50);
		cbCiclo.setLayoutY(60);
		
		labelAmbiente.setLayoutX(((pane.getWidth() - labelAmbiente.getWidth()) / 2) - 60);
		labelAmbiente.setLayoutY(60);
		txAmbiente.setLayoutX(((pane.getWidth() - txAmbiente.getWidth()) / 2) + 100);
		txAmbiente.setPrefWidth(300);
		txAmbiente.setLayoutY(60);
		
		labelArquivoDOCX.setLayoutX(((pane.getWidth() - labelArquivoDOCX.getWidth()) / 2) - 316);
		labelArquivoDOCX.setLayoutY(100);
		txArquivoDOCX.setLayoutX(((pane.getWidth() - txArquivoDOCX.getWidth()) / 2) - 100);
		txArquivoDOCX.setPrefWidth(500);
		txArquivoDOCX.setLayoutY(100);
		
		btnExecutar.setLayoutX(((pane.getWidth() - btnExecutar.getWidth()) / 2) + 130);
		btnExecutar.setLayoutY(180);
		
		/*imagem.setLayoutX(((pane.getWidth() - btnExecutar.getWidth()) / 2) - 330);
		imagem.setFitHeight(100);
		imagem.setFitWidth(100);
		imagem.setLayoutY(150);*/
		
		teclas.setLayoutX(((pane.getWidth() - btnExecutar.getWidth()) / 2) - 147);
		teclas.setFitHeight(100);
		teclas.setFitWidth(100);
		teclas.setLayoutY(150);
		
		instrucoes1.setLayoutX(((pane.getWidth() - instrucoes1.getWidth()) / 2) - 30);
		instrucoes1.setLayoutY(155);
		
		instrucoes2.setLayoutX(((pane.getWidth() - instrucoes2.getWidth()) / 2) - 16);
		instrucoes2.setLayoutY(180);
		
		instrucoes3.setLayoutX(((pane.getWidth() - instrucoes3.getWidth()) / 2) - 22);
		instrucoes3.setLayoutY(205);
		
		instrucoes4.setLayoutX(((pane.getWidth() - instrucoes4.getWidth()) / 2) - 22);
		instrucoes4.setLayoutY(230);
		
		progressBar.setLayoutX(((pane.getWidth() - btnExecutar.getWidth()) / 2) + 260);
		progressBar.setLayoutY(280);
		
		
		labelBy.setLayoutX(((pane.getWidth() - btnExecutar.getWidth()) / 2) - 350);
		labelBy.setScaleY(0.5);
		labelBy.setScaleX(0.5);
		labelBy.setLayoutY(280);
		
	}
	
	private void initListeners(){
		/*
		txModel.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 1){
		            	String retorno = selecionarPasta("Local do Modelo.docx");
			        	txModel.setText((retorno.equals("null"))? "" : retorno);
		            }
		        }
		    }
		});*/
		
		cbCiclo.getSelectionModel().selectedIndexProperty()
        .addListener(new ChangeListener<Number>() {
          @SuppressWarnings("rawtypes")
		public void changed(ObservableValue ov, Number value, Number new_value) {
            ciclo = greetings[new_value.intValue()];
          }
        });
		
		
		
		txArquivoDOCX.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 1){
		            	String retorno = selecionarArquivo("Arquivo .docx:");
			        	txArquivoDOCX.setText((retorno.equals("null"))? "" : retorno);
		            }
		        }
		    }
		});
		
		btnExecutar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Boolean erro = false;
				String mensagem = "Preencha o(s) seguinte(s) campo(s):\n";
				/*
				 * if(txModel.getText().equals("")){ mensagem = mensagem +
				 * "- Local do Modelo.docx\n"; erro = true; }
				 */
				
				if (txExecutor.getText().equals("")) {
					mensagem = mensagem + "- Executor.\n";
					erro = true;
				}
				System.out.println(ciclo);
				if (ciclo.equals("")) {
					mensagem = mensagem + "- Ciclo.\n";
					erro = true;
				}
				
				if (txAmbiente.getText().equals("")) {
					mensagem = mensagem + "- Ambiente.\n";
					erro = true;
				}
				
				if (txArquivoDOCX.getText().equals("")) {
					mensagem = mensagem + "- Arquivo .docx:\n";
					erro = true;
				}

				if (erro) {
					alert("Atenção!", mensagem);
				} else {
					try {
						progressBar.progressProperty().bind(servico.progressProperty());
						if (servico.getState() == State.SUCCEEDED || servico.getState() == State.FAILED) {
							ExecutarCasoDeTeste.passo = 1;
							ExecutarCasoDeTeste.breake = false;
							ExecutarCasoDeTeste.sair = false;
							servico.reset();
							servico.stateProperty().removeListener(listener);
							servico.stateProperty().addListener(listener);
							servico.start();
						} else if(servico.getState() == State.RUNNING){
							ExecutarCasoDeTeste.breake = true;
							servico.stateProperty().removeListener(listener);
							servico.stateProperty().addListener(listener);
						}else {
							servico.stateProperty().removeListener(listener);
							servico.stateProperty().addListener(listener);
							servico.start();
						}
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
						
					}
				}
			}
		});
	}
	
	@SuppressWarnings("unused")
	private String selecionarPasta(String texto){
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new java.io.File("."));
		directoryChooser.setTitle(texto);
		File selectedDirectory = directoryChooser.showDialog(stage);
		if(selectedDirectory == null){
			return "null";
		}else{
			return selectedDirectory.toString();
		}
	}
	
	private String selecionarArquivo(String texto){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new java.io.File("."));
	    fileChooser.setTitle(texto);
	    fileChooser.getExtensionFilters().addAll(
	            new ExtensionFilter("Word Files", "*.docx"));
	    File file = fileChooser.showOpenDialog(stage);

	    if (file == null){
	        return "null";
	    }else{
	    	return file.getPath();
	    }
	}
	
	private void alert(String title, String text){
		Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle(title);
    	alert.setHeaderText(text);
    	alert.setContentText(stage.getTitle().toString());
    	
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		//stage.getIcons().add(new Image(localIconeAplicativo));
		stage.setAlwaysOnTop(true);
    	
    	alert.showAndWait().ifPresent(rs -> {
    	    if (rs == ButtonType.OK) {
    	        System.out.println("Pressed OK.");
    	    }
    	});
	}
	
	private void alertError(String title, String text){
		Alert alert = new Alert(AlertType.ERROR);
		
		alert.setTitle(title);
    	alert.setHeaderText(text);
    	alert.setContentText(stage.getTitle().toString());
    	
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		//stage.getIcons().add(new Image(localIconeAplicativo));
		
		stage.setAlwaysOnTop(true);
		
    	
    	alert.showAndWait().ifPresent(rs -> {
    	    if (rs == ButtonType.OK) {
    	        System.out.println("Pressed OK.");
    	    }
    	});
	}
	
	@Override
	public void stop() throws Exception 
	{
	    super.stop();

	    Platform.exit();
	    System.exit(0);
	}
}


