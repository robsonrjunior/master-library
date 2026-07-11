package com.github.robsonrjunior.master.library.configs;

import com.github.robsonrjunior.master.library.model.Book;
import com.github.robsonrjunior.master.library.service.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class DatabaseSeeder {

    private static final Logger LOG = Logger.getLogger(DatabaseSeeder.class.getName());

    @Inject
    private BookService bookService;

    @Transactional
    public void onStartup(@Observes @Initialized(ApplicationScoped.class) Object event) {
        seedBooks();
    }

    private void seedBooks() {
        BookData[] books = {
            new BookData("Dom Casmurro", "Machado de Assis", "978-85-359-0277-7", 208, 1899,
                "Bentinho e Capitu, uma história de ciúme e traição no Rio de Janeiro do século XIX."),
            new BookData("Memórias Póstumas de Brás Cubas", "Machado de Assis", "978-85-359-0288-4", 176, 1881,
                "O defunto autor Brás Cubas narra suas memórias com ironia e pessimismo."),
            new BookData("Grande Sertão: Veredas", "João Guimarães Rosa", "978-85-209-0391-4", 560, 1956,
                "Riobaldo conta sua travessia pelo sertão mineiro e sua relação com Diadorim."),
            new BookData("Vidas Secas", "Graciliano Ramos", "978-85-01-04092-2", 176, 1938,
                "Uma família de retirantes enfrenta a seca e a miséria no sertão nordestino."),
            new BookData("O Alienista", "Machado de Assis", "978-85-254-0682-6", 96, 1882,
                "O Dr. Simão Bacamarte funda um hospício e interna toda a cidade de Itaguaí."),
            new BookData("Capitães da Areia", "Jorge Amado", "978-85-359-1481-4", 288, 1937,
                "Meninos abandonados vivem nas ruas de Salvador e lutam pela sobrevivência."),
            new BookData("A Hora da Estrela", "Clarice Lispector", "978-85-325-0198-1", 88, 1977,
                "Macabéa, uma nordestina ingênua, sobrevive no Rio de Janeiro."),
            new BookData("O Cortiço", "Aluísio Azevedo", "978-85-254-0688-8", 288, 1890,
                "A vida coletiva em um cortiço carioca retrata o naturalismo brasileiro."),
            new BookData("Iracema", "José de Alencar", "978-85-209-0397-6", 112, 1865,
                "A história de amor entre Iracema, a virgem dos lábios de mel, e Martim."),
            new BookData("São Bernardo", "Graciliano Ramos", "978-85-01-04125-7", 160, 1934,
                "Paulo Honório, um homem rude, conquista a fazenda São Bernardo a qualquer custo."),
            new BookData("O Guarani", "José de Alencar", "978-85-209-0395-2", 320, 1857,
                "Peri, um índio goitacá, protege a família de D. Antônio de Mariz."),
            new BookData("Macunaíma", "Mário de Andrade", "978-85-209-0394-5", 192, 1928,
                "O herói sem caráter percorre o Brasil em busca da muiraquitã perdida."),
            new BookData("Quincas Borba", "Machado de Assis", "978-85-359-0289-1", 256, 1891,
                "Rubião herda a fortuna de Quincas Borba e sua filosofia do Humanitismo."),
            new BookData("O Tempo e o Vento", "Erico Verissimo", "978-85-359-1479-1", 608, 1949,
                "A saga da família Terra Cambará no Rio Grande do Sul ao longo de 200 anos."),
            new BookData("Mayombe", "Pepetela", "978-85-359-1472-2", 176, 1980,
                "Guerrilheiros do MPLA vivem conflitos ideológicos e pessoais na floresta de Mayombe."),
            new BookData("Triste Fim de Policarpo Quaresma", "Lima Barreto", "978-85-254-0685-7", 208, 1915,
                "O major Policarpo Quaresma, patriota ingênuo, tenta salvar o Brasil com ideais utópicos."),
            new BookData("Morte e Vida Severina", "João Cabral de Melo Neto", "978-85-209-0398-3", 80, 1956,
                "Auto de Natal em que o retirante Severino enfrenta a dura realidade do sertão."),
            new BookData("A Moreninha", "Joaquim Manuel de Macedo", "978-85-254-0691-8", 176, 1844,
                "Romance de amor juvenil na ilha de Paquetá, marco do romantismo brasileiro."),
            new BookData("O Primo Basílio", "Eça de Queirós", "978-85-254-0690-1", 416, 1878,
                "Luísa, entediada com o casamento, se envolve num caso adúltero com o primo Basílio."),
            new BookData("Ensaio sobre a Cegueira", "José Saramago", "978-85-359-1478-4", 312, 1995,
                "Uma epidemia de cegueira branca revela o pior e o melhor da humanidade."),
            new BookData("1984", "George Orwell", "978-85-359-0278-4", 416, 1949,
                "Winston Smith vive sob a vigilância do Grande Irmão em uma sociedade totalitária."),
            new BookData("Cem Anos de Solidão", "Gabriel García Márquez", "978-85-01-05069-5", 448, 1967,
                "A saga da família Buendía na mítica cidade de Macondo."),
            new BookData("O Apanhador no Campo de Centeio", "J.D. Salinger", "978-85-325-0197-4", 208, 1951,
                "Holden Caulfield narra suas andanças por Nova York após ser expulso da escola."),
            new BookData("O Senhor dos Anéis", "J.R.R. Tolkien", "978-85-9508-475-1", 1200, 1954,
                "Frodo Bolseiro embarca numa jornada épica para destruir o Um Anel."),
            new BookData("O Pequeno Príncipe", "Antoine de Saint-Exupéry", "978-85-220-0046-7", 96, 1943,
                "Um príncipe de outro planeta ensina lições sobre amor e amizade."),
            new BookData("Crime e Castigo", "Fiódor Dostoiévski", "978-85-254-0692-5", 592, 1866,
                "Raskólnikov comete um assassinato e enfrenta as consequências psicológicas."),
            new BookData("Orgulho e Preconceito", "Jane Austen", "978-85-254-0683-3", 424, 1813,
                "Elizabeth Bennet e Mr. Darcy superam diferenças sociais em busca do amor."),
            new BookData("A Revolução dos Bichos", "George Orwell", "978-85-359-0280-7", 112, 1945,
                "Animais se rebelam contra os humanos e criam uma sociedade corrupta."),
            new BookData("Ulisses", "James Joyce", "978-85-359-1480-7", 736, 1922,
                "Um dia na vida de Leopold Bloom em Dublin, paralelo à Odisseia de Homero."),
            new BookData("O Sol é Para Todos", "Harper Lee", "978-85-030-1162-6", 352, 1960,
                "Atticus Finch defende um homem negro injustamente acusado no sul dos EUA."),
            new BookData("O Velho e o Mar", "Ernest Hemingway", "978-85-286-0107-7", 128, 1952,
                "O pescador Santiago luta contra um gigantesco marlim em alto-mar."),
            new BookData("Fahrenheit 451", "Ray Bradbury", "978-85-250-1074-2", 208, 1953,
                "Guy Montag queima livros em uma sociedade que proíbe a leitura."),
            new BookData("Lolita", "Vladimir Nabokov", "978-85-359-1482-1", 368, 1955,
                "Humbert Humbert narra sua obsessão pela jovem Dolores Haze."),
            new BookData("O Nome da Rosa", "Umberto Eco", "978-85-01-03020-5", 560, 1980,
                "Monges morrem misteriosamente em uma abadia medieval do século XIV."),
            new BookData("O Conde de Monte Cristo", "Alexandre Dumas", "978-85-254-0693-2", 1312, 1844,
                "Edmond Dantès é preso injustamente e busca vingança após encontrar um tesouro."),
            new BookData("Madame Bovary", "Gustave Flaubert", "978-85-254-0689-5", 384, 1857,
                "Emma Bovary busca escapar da vida provinciana através de amantes e luxo."),
            new BookData("O Processo", "Franz Kafka", "978-85-359-0281-4", 224, 1925,
                "Josef K. é preso sem explicação e enfrenta um sistema judicial absurdo."),
            new BookData("A Metamorfose", "Franz Kafka", "978-85-359-0282-1", 96, 1915,
                "Gregor Samsa acorda transformado em um inseto monstruoso."),
            new BookData("O Retrato de Dorian Gray", "Oscar Wilde", "978-85-254-0694-9", 256, 1890,
                "Dorian Gray permanece jovem enquanto seu retrato envelhece com seus pecados."),
            new BookData("Duna", "Frank Herbert", "978-85-7657-346-4", 688, 1965,
                "Paul Atreides enfrenta intrigas políticas no planeta deserto Arrakis."),
            new BookData("Admirável Mundo Novo", "Aldous Huxley", "978-85-250-1075-9", 312, 1932,
                "Uma sociedade futurista onde humanos são geneticamente condicionados."),
            new BookData("O Estrangeiro", "Albert Camus", "978-85-01-01086-7", 128, 1942,
                "Meursault comete um crime e enfrenta a indiferença do absurdo existencial."),
            new BookData("Os Miseráveis", "Victor Hugo", "978-85-254-0686-4", 1504, 1862,
                "Jean Valjean busca redenção enquanto é perseguido pelo inspetor Javert."),
            new BookData("Guerra e Paz", "Leon Tolstói", "978-85-254-0695-6", 1408, 1869,
                "A nobreza russa enfrenta as Guerras Napoleônicas e busca o sentido da vida."),
            new BookData("O Morro dos Ventos Uivantes", "Emily Brontë", "978-85-254-0684-0", 368, 1847,
                "Heathcliff e Catherine vivem um amor tempestuoso nas charnecas de Yorkshire."),
            new BookData("O Grande Gatsby", "F. Scott Fitzgerald", "978-85-254-1387-2", 192, 1925,
                "Jay Gatsby persegue o sonho americano e o amor de Daisy Buchanan."),
            new BookData("Anna Karenina", "Leon Tolstói", "978-85-254-0696-3", 864, 1877,
                "Anna abandona o marido por um amor arrebatador e enfrenta a hipocrisia social."),
            new BookData("Moby Dick", "Herman Melville", "978-85-254-0687-1", 720, 1851,
                "O capitão Ahab persegue obsessivamente a baleia branca Moby Dick."),
            new BookData("A Divina Comédia", "Dante Alighieri", "978-85-254-0697-0", 576, 1320,
                "Dante percorre o Inferno, Purgatório e Paraíso guiado por Virgílio e Beatriz."),
            new BookData("Dom Quixote", "Miguel de Cervantes", "978-85-254-0698-7", 928, 1605,
                "O fidalgo Alonso Quijano enlouquece ao ler livros de cavalaria."),
        };

        for (BookData data : books) {
            try {
                List<Book> existing = bookService.search(data.title);
                if (existing.isEmpty()) {
                    Book book = new Book();
                    book.setTitle(data.title);
                    book.setAuthor(data.author);
                    book.setIsbn(data.isbn);
                    book.setPageCount(data.pageCount);
                    book.setPublishedYear(data.publishedYear);
                    book.setDescription(data.description);
                    bookService.create(book);
                    LOG.info("Seed: book created - " + data.title);
                } else {
                    LOG.info("Seed: book already exists - " + data.title);
                }
            } catch (Exception e) {
                LOG.warning("Seed: failed to create book '" + data.title + "' - " + e.getMessage());
            }
        }

        LOG.info("Seed: books seeding completed");
    }

    private record BookData(String title, String author, String isbn, Integer pageCount, Integer publishedYear,
            String description) {}
}
