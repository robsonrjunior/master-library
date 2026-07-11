package com.github.robsonrjunior.master.library.configs;

import com.github.robsonrjunior.master.library.model.Book;
import com.github.robsonrjunior.master.library.model.Movie;
import com.github.robsonrjunior.master.library.model.Series;
import com.github.robsonrjunior.master.library.model.SeriesStatus;
import com.github.robsonrjunior.master.library.model.User;
import com.github.robsonrjunior.master.library.model.UserRating;
import com.github.robsonrjunior.master.library.repository.MediaItemRepository;
import com.github.robsonrjunior.master.library.repository.UserRatingRepository;
import com.github.robsonrjunior.master.library.repository.UserRepository;
import com.github.robsonrjunior.master.library.service.BookService;
import com.github.robsonrjunior.master.library.service.MovieService;
import com.github.robsonrjunior.master.library.service.SeriesService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class DatabaseSeeder {

    private static final Logger LOG = Logger.getLogger(DatabaseSeeder.class.getName());

    @Inject
    private BookService bookService;

    @Inject
    private MovieService movieService;

    @Inject
    private SeriesService seriesService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private MediaItemRepository mediaItemRepository;

    @Inject
    private UserRatingRepository ratingRepository;

    @Transactional
    public void onStartup(@Observes @Initialized(ApplicationScoped.class) Object event) {
        seedBooks();
        seedMovies();
        seedSeries();
        seedUsers();
        seedRatings();
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

    private record MovieData(String title, String director, Integer runtimeMinutes,
            Integer releaseYear, String description) {}

    private record SeriesData(String title, Integer numberOfSeasons, Integer numberOfEpisodes,
            Integer startYear, Integer endYear, SeriesStatus status, String description) {}

    private record UserData(String username, String email, String displayName) {}

    private void seedMovies() {
        MovieData[] movies = {
            new MovieData("O Poderoso Chefao", "Francis Ford Coppola", 175, 1972,
                "Don Vito Corleone comanda uma familia mafiosa em Nova York enquanto seu filho Michael se envolve nos negocios."),
            new MovieData("Cidade de Deus", "Fernando Meirelles", 130, 2002,
                "Busca-Pe narra a violencia e o trafico na Cidade de Deus, favela do Rio de Janeiro."),
            new MovieData("O Senhor dos Aneis: A Sociedade do Anel", "Peter Jackson", 178, 2001,
                "Frodo e a Sociedade do Anel partem para destruir o Um Anel na Montanha da Perdicao."),
            new MovieData("Pulp Fiction", "Quentin Tarantino", 154, 1994,
                "Historias entrelacadas de gangsters, boxeadores e bandidos de Los Angeles."),
            new MovieData("Central do Brasil", "Walter Salles", 113, 1998,
                "Dora, uma ex-professora, acompanha o menino Josue em busca de seu pai pelo interior do Brasil."),
            new MovieData("Matrix", "Lana Wachowski", 136, 1999,
                "Neo descobre que a realidade é uma simulacao e se une a rebeliao contra as maquinas."),
            new MovieData("Tropa de Elite", "Jose Padilha", 115, 2007,
                "Capitao Nascimento enfrenta o trafico e a corrupcao no BOPE do Rio de Janeiro."),
            new MovieData("O Auto da Compadecida", "Guel Arraes", 104, 2000,
                "Joao Grilo e Chico se envolvem em confusoes no sertao nordestino com muito humor."),
            new MovieData("Parasita", "Bong Joon-ho", 132, 2019,
                "Uma familia pobre se infiltra na casa de uma familia rica, gerando consequencias imprevisiveis."),
            new MovieData("Interestelar", "Christopher Nolan", 169, 2014,
                "Astronautas viajam por um buraco de minhoca em busca de um novo lar para a humanidade."),
            new MovieData("O Silencio dos Inocentes", "Jonathan Demme", 118, 1991,
                "A agente Starling entrevista o Dr. Hannibal Lecter para capturar um serial killer."),
            new MovieData("Bacurau", "Kleber Mendonca Filho", 131, 2019,
                "Moradores de Bacurau percebem que sua cidade desapareceu do mapa e enfrentam invasores."),
            new MovieData("Forrest Gump", "Robert Zemeckis", 142, 1994,
                "Forrest Gump, um homem simples, vive momentos historicos dos EUA enquanto busca seu amor Jenny."),
            new MovieData("A Lista de Schindler", "Steven Spielberg", 195, 1993,
                "Oskar Schindler salva mais de mil judeus durante o Holocausto usando sua fabrica."),
            new MovieData("O Labirinto do Fauno", "Guillermo del Toro", 118, 2006,
                "Na Espanha fascista, a menina Ofelia descobre um mundo fantastico e perigoso."),
            new MovieData("Clube da Luta", "David Fincher", 139, 1999,
                "Um homem insone e um vendedor de sabao criam um clube secreto de luta que sai do controle."),
            new MovieData("O Irlandes", "Martin Scorsese", 209, 2019,
                "Frank Sheeran relembra sua vida como assassino de aluguel ligado ao desaparecimento de Jimmy Hoffa."),
            new MovieData("Toy Story", "John Lasseter", 81, 1995,
                "Os brinquedos de Andy ganham vida e vivem aventuras quando ninguem esta vendo."),
            new MovieData("Mad Max: Estrada da Furia", "George Miller", 120, 2015,
                "Em um deserto pos-apocaliptico, Max se une a Furiosa para fugir de um tirano."),
            new MovieData("Whiplash", "Damien Chazelle", 106, 2014,
                "Um jovem baterista enfrenta um professor abusivo em busca da perfeicao musical."),
        };

        for (MovieData data : movies) {
            try {
                List<Movie> existing = movieService.search(data.title);
                if (existing.isEmpty()) {
                    Movie movie = new Movie();
                    movie.setTitle(data.title);
                    movie.setDirector(data.director);
                    movie.setRuntimeMinutes(data.runtimeMinutes);
                    movie.setReleaseYear(data.releaseYear);
                    movie.setDescription(data.description);
                    movieService.create(movie);
                    LOG.info("Seed: movie created - " + data.title);
                } else {
                    LOG.info("Seed: movie already exists - " + data.title);
                }
            } catch (Exception e) {
                LOG.warning("Seed: failed to create movie '" + data.title + "' - " + e.getMessage());
            }
        }

        LOG.info("Seed: movies seeding completed");
    }

    private void seedSeries() {
        SeriesData[] series = {
            new SeriesData("Breaking Bad", 5, 62, 2008, 2013, SeriesStatus.ENDED,
                "Walter White, um professor de quimica, se torna um grande produtor de metanfetamina."),
            new SeriesData("Game of Thrones", 8, 73, 2011, 2019, SeriesStatus.ENDED,
                "Nove familias nobres lutam pelo Trono de Ferro nos Sete Reinos de Westeros."),
            new SeriesData("Stranger Things", 4, 34, 2016, null, SeriesStatus.ONGOING,
                "Um grupo de amigos enfrenta forcas sobrenaturais na pequena cidade de Hawkins."),
            new SeriesData("The Crown", 6, 60, 2016, 2023, SeriesStatus.ENDED,
                "A cronica do reinado da Rainha Elizabeth II e os eventos que moldaram o seculo XX."),
            new SeriesData("Dark", 3, 26, 2017, 2020, SeriesStatus.ENDED,
                "O desaparecimento de criancas revela uma complexa rede de viagens no tempo em Winden."),
            new SeriesData("The Mandalorian", 3, 24, 2019, null, SeriesStatus.ONGOING,
                "Um cacador de recompensas solitario viaja pela galaxia protegendo uma crianca misteriosa."),
            new SeriesData("Arcane", 2, 18, 2021, null, SeriesStatus.ONGOING,
                "Duas irmas se encontram em lados opostos de um conflito entre Piltover e Zaun."),
            new SeriesData("Chernobyl", 1, 5, 2019, 2019, SeriesStatus.ENDED,
                "A historia real do desastre nuclear de Chernobyl e a luta para conter a catastrofe."),
            new SeriesData("The Last of Us", 1, 9, 2023, null, SeriesStatus.ONGOING,
                "Joel e Ellie atravessam uma America devastada por uma pandemia fungica."),
            new SeriesData("Band of Brothers", 1, 10, 2001, 2001, SeriesStatus.ENDED,
                "A historia real da Easy Company durante a Segunda Guerra Mundial, da Normandia ao fim do conflito."),
        };

        for (SeriesData data : series) {
            try {
                List<Series> existing = seriesService.search(data.title);
                if (existing.isEmpty()) {
                    Series s = new Series();
                    s.setTitle(data.title);
                    s.setNumberOfSeasons(data.numberOfSeasons);
                    s.setNumberOfEpisodes(data.numberOfEpisodes);
                    s.setStartYear(data.startYear);
                    s.setEndYear(data.endYear);
                    s.setStatus(data.status);
                    s.setDescription(data.description);
                    seriesService.create(s);
                    LOG.info("Seed: series created - " + data.title);
                } else {
                    LOG.info("Seed: series already exists - " + data.title);
                }
            } catch (Exception e) {
                LOG.warning("Seed: failed to create series '" + data.title + "' - " + e.getMessage());
            }
        }

        LOG.info("Seed: series seeding completed");
    }

    private void seedUsers() {
        UserData[] users = {
            new UserData("alice", "alice@email.com", "Alice Silva"),
            new UserData("bruno", "bruno@email.com", "Bruno Costa"),
            new UserData("camila", "camila@email.com", "Camila Oliveira"),
            new UserData("diego", "diego@email.com", "Diego Santos"),
            new UserData("eliane", "eliane@email.com", "Eliane Ferreira"),
        };

        for (UserData data : users) {
            try {
                if (userRepository.findByUsername(data.username).isEmpty()
                    && userRepository.findByEmail(data.email).isEmpty()) {
                    User user = new User();
                    user.setUsername(data.username);
                    user.setEmail(data.email);
                    user.setDisplayName(data.displayName);
                    userRepository.save(user);
                    LOG.info("Seed: user created - " + data.username);
                } else {
                    LOG.info("Seed: user already exists - " + data.username);
                }
            } catch (Exception e) {
                LOG.warning("Seed: failed to create user '" + data.username + "' - " + e.getMessage());
            }
        }

        LOG.info("Seed: users seeding completed");
    }

    private void seedRatings() {
        List<User> users = userRepository.findAll();
        List<com.github.robsonrjunior.master.library.model.MediaItem> mediaItems =
            mediaItemRepository.findAll();

        if (users.isEmpty() || mediaItems.isEmpty()) {
            LOG.info("Seed: ratings skipped - no users or media items available");
            return;
        }

        User alice = findUser(users, "alice");
        User bruno = findUser(users, "bruno");
        User camila = findUser(users, "camila");
        User diego = findUser(users, "diego");
        User eliane = findUser(users, "eliane");

        addRatingIfAbsent(alice, "Dom Casmurro", mediaItems, new BigDecimal("9.5"),
            "Um classico absoluto. Machado de Assis nunca decepciona.");
        addRatingIfAbsent(bruno, "Dom Casmurro", mediaItems, new BigDecimal("9.0"),
            "Leitura obrigatoria para entender a literatura brasileira.");
        addRatingIfAbsent(camila, "Grande Sertao: Veredas", mediaItems, new BigDecimal("10.0"),
            "Guimaraes Rosa revolucionou a lingua portuguesa com esta obra-prima.");
        addRatingIfAbsent(diego, "1984", mediaItems, new BigDecimal("9.0"),
            "Assustadoramente atual. Uma critica poderosa ao totalitarismo.");
        addRatingIfAbsent(eliane, "Cem Anos de Solidao", mediaItems, new BigDecimal("9.5"),
            "Garcia Marquez criou um universo magico e inesquecivel.");

        addRatingIfAbsent(alice, "O Poderoso Chefao", mediaItems, new BigDecimal("10.0"),
            "O melhor filme de todos os tempos. Atuacoes impecaveis.");
        addRatingIfAbsent(bruno, "Cidade de Deus", mediaItems, new BigDecimal("9.0"),
            "Retrato cru e realista do Rio de Janeiro. Cinema nacional no auge.");
        addRatingIfAbsent(camila, "Parasita", mediaItems, new BigDecimal("9.5"),
            "Uma critica social brilhante com reviravoltas surpreendentes.");
        addRatingIfAbsent(diego, "Matrix", mediaItems, new BigDecimal("8.5"),
            "Revolucionou os efeitos visuais e trouxe questoes filosoficas profundas.");
        addRatingIfAbsent(eliane, "Forrest Gump", mediaItems, new BigDecimal("8.5"),
            "Emocionante e inspirador. Tom Hanks esta genial.");

        addRatingIfAbsent(alice, "Breaking Bad", mediaItems, new BigDecimal("10.0"),
            "A transformacao de Walter White é uma das melhores da TV.");
        addRatingIfAbsent(bruno, "Chernobyl", mediaItems, new BigDecimal("10.0"),
            "Seriao intenso e assustadoramente real. Producao impecavel.");
        addRatingIfAbsent(camila, "Dark", mediaItems, new BigDecimal("9.5"),
            "A serie de viagem no tempo mais bem amarrada que ja assisti.");
        addRatingIfAbsent(diego, "Game of Thrones", mediaItems, new BigDecimal("8.0"),
            "As primeiras temporadas sao perfeitas. O final deixou a desejar.");
        addRatingIfAbsent(eliane, "Stranger Things", mediaItems, new BigDecimal("8.5"),
            "Nostalgia anos 80 misturada com terror e ficcao cientifica.");

        LOG.info("Seed: ratings seeding completed");
    }

    private User findUser(List<User> users, String username) {
        return users.stream()
            .filter(u -> username.equals(u.getUsername()))
            .findFirst()
            .orElse(null);
    }

    private void addRatingIfAbsent(
        User user,
        String mediaTitle,
        List<com.github.robsonrjunior.master.library.model.MediaItem> mediaItems,
        BigDecimal score,
        String comment
    ) {
        if (user == null) {
            return;
        }
        com.github.robsonrjunior.master.library.model.MediaItem media = mediaItems.stream()
            .filter(m -> mediaTitle.equals(m.getTitle()))
            .findFirst()
            .orElse(null);
        if (media == null) {
            return;
        }
        try {
            if (ratingRepository.findByUserAndMedia(user, media).isPresent()) {
                return;
            }
            UserRating rating = new UserRating();
            rating.setUser(user);
            rating.setMedia(media);
            rating.setScore(score);
            rating.setComment(comment);
            ratingRepository.save(rating);
            LOG.info("Seed: rating created - " + user.getUsername() + " / " + media.getTitle());
        } catch (Exception e) {
            LOG.warning("Seed: failed to create rating for '" + mediaTitle + "' - " + e.getMessage());
        }
    }
}
