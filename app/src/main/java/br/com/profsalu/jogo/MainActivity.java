package br.com.profsalu.jogo;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Criando as variaveis que receberam as views
    EditText entrada_txt;
    TextView tentativas_txt;
    TextView historico_txt;
    Button botao_btn;
    //Armazenara o numero sorteado
    Integer sorteio;
    //Contabiliza os erros do jogador
    Integer tentativas = 0;
    //Armazena os numeros errados inseridos pelo jogador
    String historico = "";

    AlertDialog.Builder confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sorteia um numero inteiro entre 0 e 100/
        sorteio = sorteiaNumero();

        //Recebendo as views do arquivo de layout(.xml)
        entrada_txt = (EditText) findViewById(R.id.numero);
        tentativas_txt = (TextView) findViewById(R.id.tentativas);
        historico_txt = (TextView) findViewById(R.id.historico);
        botao_btn = (Button) findViewById(R.id.jogar);

        //Exibe o numero sorteado
        //Toast.makeText(MainActivity.this, Integer.toString(sorteio), Toast.LENGTH_LONG).show();

        botao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Convertendo o numero digitado para inteiro.



                if (!entrada_txt.getText().toString().equals("")) {
                    Integer numeroDigitado = Integer.parseInt(entrada_txt.getText().toString());

                    if(testaNumeroDigitado(numeroDigitado)) {


                        if (numeroDigitado > sorteio) {
                            //Exibe uma mensagem caso o numero digitado for MAIOR que o sorteado
                            Toast.makeText(MainActivity.this, //Em qual activity sera exibida a mensagem
                                    "O numero digitado é MAIOR que o sorteado", //A String que sera exibida
                                    Toast.LENGTH_SHORT).show();//A duracao da mensagem exibida

                            //Captura o valor digitado e o armazena dentro da variavel historico
                            historico += entrada_txt.getText().toString() + " ";
                            //Joga o valor da variavel historico dentro do arquivo de layout
                            historico_txt.setText(historico);
                            //Incrementa o contador de erros
                            tentativas++;
                            //Exibe a quantidade de erros atual
                            tentativas_txt.setText("Tentativas: " + tentativas);
                            //Limpando o campo apos o jogador clicar no botao
                            entrada_txt.setText("");

                        } else if (numeroDigitado < sorteio) {
                            //Exibe uma mensagem caso o numero digitado for MENOR que o sorteado

                            Toast.makeText(MainActivity.this, //Em qual activity sera exibida a mensagem
                                    "O numero digitado é MENOR que o sorteado", //A String que sera exibida
                                    Toast.LENGTH_SHORT).show();//A duracao da mensagem exibida

                            //Captura o valor digitado e o armazena dentro da variavel historico
                            historico += entrada_txt.getText().toString() + " ";
                            //Joga o valor da variavel historico dentro do arquivo de layout
                            historico_txt.setText(historico);
                            //Incrementa o contador de erros
                            tentativas++;
                            //Exibe a quantidade de erros atual
                            tentativas_txt.setText("Tentativas: " + tentativas);
                            //Limpando o campo apos o jogador clicar no botao
                            entrada_txt.setText("");
                        } else {
                            //Exibe uma mensagem caso o numero digitado for IGUAL ao sorteado
                            Toast.makeText(MainActivity.this,
                                    "Parabens você acertou!",
                                    Toast.LENGTH_SHORT).show();

                            confirm = new AlertDialog.Builder(MainActivity.this);

                            confirm.setTitle("Parabéns!");
                            confirm.setMessage("Deseja jogar de novo? ");
                            confirm.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    resetGame();
                                }
                            });
                            confirm.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            confirm.show();

                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Número inválido! \nDigite um valor entre 0 e 100", Toast.LENGTH_SHORT).show();
                        entrada_txt.setText("");
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Número inválido! \nDigite um valor entre 0 e 100", Toast.LENGTH_SHORT).show();
                    entrada_txt.setText("");
                }
            }

        });

        //Confirmando via teclado do android ao inves do click no botao
        entrada_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    botao_btn.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    private Integer sorteiaNumero() {
        return (int) (Math.random() * 100);
    }

    private void resetGame() {
        entrada_txt.setText("");
        historico_txt.setText("");
        tentativas = 0;
        tentativas_txt.setText("Tentativas: " + tentativas);
        sorteio = sorteiaNumero();
    }

    private Boolean testaNumeroDigitado(Integer numero) {
        if (numero >= 0 && numero <= 100) {
            return true;
        } else {
            return false;
        }
    }
}
