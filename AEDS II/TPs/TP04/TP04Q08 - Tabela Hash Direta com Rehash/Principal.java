import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

class Hash {

  public class Jogador {

    public int id;
    public String nome;
    public int altura;
    public int peso;
    public String universidade;
    public int anoNascimento;
    public String cidadeNascimento;
    public String estadoNascimento;
    public String[] dados;

    public Jogador(
      int id,
      String nome,
      int altura,
      int peso,
      String universidade,
      int anoNascimento,
      String cidadeNascimento,
      String estadoNascimento
    ) {
      this.id = id;
      this.nome = nome;
      this.altura = altura;
      this.peso = peso;
      this.universidade = universidade;
      this.anoNascimento = anoNascimento;
      this.cidadeNascimento = cidadeNascimento;
      this.estadoNascimento = estadoNascimento;
    }

    public Jogador() {
      this.id = -1;
      this.nome = "";
      this.altura = -1;
      this.peso = -1;
      this.universidade = "";
      this.anoNascimento = -1;
      this.cidadeNascimento = "";
      this.estadoNascimento = "";
    }

    public Jogador(int id) {
      this();
      this.ler(id);
    }

    public String getNome() {
      return this.nome;
    }

    public void setNome(String nome) {
      this.nome = nome;
    }

    public int getId() {
      return this.id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getAltura() {
      return this.altura;
    }

    public void setAltura(int altura) {
      this.altura = altura;
    }

    public int getPeso() {
      return this.peso;
    }

    public void setPeso(int peso) {
      this.peso = peso;
    }

    public String getUniversidade() {
      return this.universidade;
    }

    public void setUniversidade(String universidade) {
      this.universidade = universidade;
    }

    public int getAnoNascimento() {
      return this.anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
      this.anoNascimento = anoNascimento;
    }

    public String getCidadeNascimento() {
      return this.cidadeNascimento;
    }

    public void setCidadeNascimento(String cidadeNascimento) {
      this.cidadeNascimento = cidadeNascimento;
    }

    public String getEstadoNascimento() {
      return this.estadoNascimento;
    }

    public void setEstadoNascimento(String estadoNascimento) {
      this.estadoNascimento = estadoNascimento;
    }

    public void getDados(String arq) {
      try {
        RandomAccessFile sc = new RandomAccessFile(arq, "r");
        String[] playerInfo = new String[10000];
        int i = 0;
        while (sc.getFilePointer() < sc.length()) {
          playerInfo[i] = sc.readLine();
          i++;
        }
        dados = playerInfo;
        sc.close();
      } catch (IOException e) {
        System.out.println("ERRO AO LER ARQUIVO" + e.getMessage());
      }
    }

    public void ler(int id) {
      id++;
      if (dados == null) {
        getDados("/tmp/players.csv");
      }
      String[] props = new String[8];

      String str = dados[id];
      int j = 0;
      props[0] = "";
      for (int i = 0; i < str.length(); i++) {
        if (str.charAt(i) == ',') {
          if (props[j] == "") {
            props[j] = "nao informado";
          }
          j++;
          props[j] = "";
        } else {
          props[j] += str.charAt(i);
        }
      }

      if (props[7] == "") {
        props[7] = "nao informado";
      }

      this.id = Integer.parseInt(props[0]);
      this.nome = props[1];
      this.altura = Integer.parseInt(props[2]);
      this.peso = Integer.parseInt(props[3]);
      this.universidade = props[4];
      this.anoNascimento = Integer.parseInt(props[5]);
      this.cidadeNascimento = props[6];
      this.estadoNascimento = props[7];
    }
  }

  // Aqui se inicia a tabela de verdade, após todo o tratamento necessário para a leitura e armazenamento de jogadores

  int comparacoes = 0;
  int tamTab;
  Jogador[] tabela;

  public Hash(int tamTab) {
    this.tamTab = tamTab;
    this.tabela = new Jogador[tamTab];

    // Inicializar todas as posições da tabela com null

    for (int i = 0; i < tamTab; i++) {
      this.tabela[i] = null;
    }
  }

  // Funcao de hash

  public int hash(Jogador jogador) {
    return jogador.altura % tamTab;
  }

  // Funcao de rehash

  public int rehash(Jogador jogador) {
    return (jogador.altura + 1) % tamTab;
  }

  // Insercao de um jogador na tabela

  public boolean insert(Jogador jogador) {
    comparacoes++;
    if (jogador == null) {
      return false;
    }

    int index = hash(jogador);
    if (tabela[index] == null) {
      tabela[index] = jogador;
      return true;
    }

    int trueIndex = rehash(jogador);
    if (tabela[trueIndex] == null) {
      tabela[trueIndex] = jogador;
      return true;
    }

    return false;
  }

  // Funcao de pesquisa na tabela

  public boolean search(String nome) {
    for (int i = 0; i < tamTab; i++) {
      comparacoes++;
      if (tabela[i] != null && tabela[i].nome.equals(nome)) {
        return true;
      }
    }
    return false;
  }
}

public class Principal {

  public static void main(String[] args) {
    long inicio = System.nanoTime();

    // PARTE 1 DO EXERCICIO (LEITURA E ARMAZENAMENTO DE JOGADORES)

    Scanner sc = new Scanner(System.in);
    Hash tabelaAlpha = new Hash(25);
    String entrada = sc.nextLine();
    while (true) {
      if (entrada.equals("FIM")) {
        break;
      }
      tabelaAlpha.insert(tabelaAlpha.new Jogador(Integer.parseInt(entrada)));
      entrada = sc.nextLine();
    }

    // PARTE 2 DO EXERCICIO (PESQUISA DE JOGADORES NA TABELA HASH)

    entrada = sc.nextLine();
    while (true) {
      if (entrada.equals("FIM")) {
        break;
      }
      if (tabelaAlpha.search(entrada)) {
        System.out.println(entrada + " SIM");
      } else {
        System.out.println(entrada + " NAO");
      }
      entrada = sc.nextLine();
    }
    sc.close();

    // PARTE 3 DO EXERICIO (REGISTRO DE MATRICULA)

    try {
      RandomAccessFile matricula = new RandomAccessFile(
        "matricula_hashRehash.txt",
        "rw"
      );
      long fim = System.nanoTime();
      double tempo = (double) fim - (double) inicio;
      matricula.writeBytes(
        "805688" + "\t" + tempo / 10000000 + "\t" + tabelaAlpha.comparacoes
      );
      matricula.close();
    } catch (IOException e) {
      System.out.println("FATAL ERROR" + e.getMessage());
    }
  }
}
