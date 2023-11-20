import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
class Jogador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String funcao;
    private String nomeTime;
    private String patente;

    
}

public class Main {
    public static void main(String[] args) {
       
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Jogador.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();

       
        inserirJogadores(sessionFactory);

        
        imprimirDadosJogadores(sessionFactory);

        
        sessionFactory.close();
    }

    private static void inserirJogadores(SessionFactory sessionFactory) {
        Jogador jogador1 = new Jogador("Jogador1", "Awp", "TimeA", "Ouro");
        Jogador jogador2 = new Jogador("Jogador2", "Entry Fragger", "TimeB", "Prata");
        Jogador jogador3 = new Jogador("Jogador3", "Support", "TimeC", "Global Elite");

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(jogador1);
            session.save(jogador2);
            session.save(jogador3);

            session.getTransaction().commit();
        }
    }

    private static void imprimirDadosJogadores(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            List<Jogador> jogadores = session.createQuery("FROM Jogador", Jogador.class).list();

            for (Jogador jogador : jogadores) {
                System.out.println("ID: " + jogador.getId());
                System.out.println("Nome: " + jogador.getNome());
                System.out.println("Função: " + jogador.getFuncao());
                System.out.println("Time: " + jogador.getNomeTime());
                System.out.println("Patente: " + jogador.getPatente());
                System.out.println("--------------");
            }
        }
    }
}
