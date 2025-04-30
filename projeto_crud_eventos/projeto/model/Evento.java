package projeto.model;

       import java.time.LocalDate;

       public class Evento {
           private int id;
           private String nome;
           private LocalDate data;
           private String local;
           private String descricao;
           private String nomeParticipante;

           public String getNomeParticipante() {
               return nomeParticipante;
           }
           
           public void setNomeParticipante(String nomeParticipante) {
               this.nomeParticipante = nomeParticipante;
           }
           
           public Evento() {}
           

           public Evento(int id, String nome, LocalDate data, String local, String descricao) {
            this.id = id;
            this.nome = nome;
            this.data = data;
            this.local = local;
            this.descricao = descricao;
        }


           public Evento(String nome, LocalDate data, String local, String descricao) {
               this.nome = nome;
               this.data = data;
               this.local = local;
               this.descricao = descricao;
           }

           public int getId() { return id; }
           public void setId(int id) { this.id = id; }
           public String getNome() { return nome; }
           public void setNome(String nome) { this.nome = nome; }
           public LocalDate getData() { return data; }
           public void setData(LocalDate data) { this.data = data; }
           public String getLocal() { return local; }
           public void setLocal(String local) { this.local = local; }
           public String getDescricao(){ return descricao;}
           public void setDescricao(String descricao){ this.descricao = descricao;}

           @Override
           public String toString() {
            return "Evento{id=" + id + 
                   ", nome='" + nome + 
                   "', data=" + data + 
                   ", local='" + local + 
                   "', participante='" + nomeParticipante + "'}";
        }
    } 