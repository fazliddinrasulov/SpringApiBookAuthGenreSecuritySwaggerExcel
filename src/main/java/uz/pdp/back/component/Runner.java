package uz.pdp.back.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.back.entity.*;
import uz.pdp.back.entity.enums.RoleEnum;
import uz.pdp.back.service.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final AttachmentService attachmentService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws IOException {
        if (ddl.equals("create")) {
            List<Role> roles = addRoles();
            addUsers(roles);
            List<Author> authors = addAuthors();
            List<Genre> genres = addGenres();
            addBooks(authors, genres);

        }
    }

    private List<Role> addRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.save(Role.builder().role(RoleEnum.ROLE_ADMIN).build()));
        roles.add(roleService.save(Role.builder().role(RoleEnum.ROLE_USER).build()));
        return roles;
    }

    private List<User> addUsers(List<Role> roles) {
        List<User> users = new ArrayList<>();
        users.add(userService.save(User.builder()
                .username("fazliddin0815@gmail.com")
                .password(passwordEncoder.encode("qwe"))
                .roles(List.of(roles.get(0)))
                .build()));
        users.add(userService.save(User.builder()
                .username("azizbek@gmail.com")
                .password(passwordEncoder.encode("qwe"))
                .roles(List.of(roles.get(1)))
                .build()));
        users.add(userService.save(User.builder()
                .username("ibrohim@gmail.com")
                .password(passwordEncoder.encode("qwe"))
                .roles(List.of(roles.get(1)))
                .build()));

        return users;
    }

    private List<Author> addAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(authorService.save(Author.builder()
                .name("author-1")
                .build()));
        authors.add(authorService.save(Author.builder()
                .name("author-2")
                .build()));
        authors.add(authorService.save(Author.builder()
                .name("author-3")
                .build()));
        authors.add(authorService.save(Author.builder()
                .name("author-4")
                .build()));
        return authors;
    }

    private List<Genre> addGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(genreService.save(Genre.builder()
                .name("genre-1")
                .build()));
        genres.add(genreService.save(Genre.builder()
                .name("genre-2")
                .build()));
        genres.add(genreService.save(Genre.builder()
                .name("genre-3")
                .build()));
        genres.add(genreService.save(Genre.builder()
                .name("genre-4")
                .build()));
        genres.add(genreService.save(Genre.builder()
                .name("genre-5")
                .build()));
        return genres;
    }

    private void addBooks(List<Author> authors, List<Genre> genres) throws IOException {
        Random random = new Random();
        for (int i = 0; i < authors.size(); i++) {
            for (int j = 0; j < 5; j++) {
                Book savedBook = bookService.save(Book.builder()
                        .title("book-" + (i * 10 + j))
                        .author(authors.get(i))
                        .genre(genres.get(random.nextInt(authors.size())))
                        .build());
                attachmentService.save(Attachment.builder()
                        .book(savedBook)
                        .data(Files.readAllBytes(Path.of(System.getProperty("user.dir") + "/book.jpeg")))
                        .build());
            }
        }
    }
}
