package com.mangajutsu.webclient.proxies;

import java.util.List;

import com.mangajutsu.webclient.models.AnimeModel;
import com.mangajutsu.webclient.models.FileModel;
import com.mangajutsu.webclient.models.MangaModel;
import com.mangajutsu.webclient.models.MovieModel;
import com.mangajutsu.webclient.models.ReviewModel;
import com.mangajutsu.webclient.models.RoleModel;
import com.mangajutsu.webclient.models.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mangajutsu-api", url = "localhost:9090")
public interface MangajutsuProxy {

        // User //

        @GetMapping("/find-user/{username}")
        UserModel findUserByUsername(@PathVariable("username") String username);

        @GetMapping("/user-details/{id}")
        UserModel getUserDetails(@PathVariable Integer id);

        @PostMapping("/register")
        void userRegistration(@RequestBody UserModel user);

        @GetMapping("/register/verify")
        boolean verifyAccount(@RequestParam("token") String token);

        @DeleteMapping("/delete-unverified-account")
        void deleteUnverifiedAccount();

        @PostMapping("/register-login-failure")
        void registerLoginFailure(@RequestBody String username);

        @PostMapping("/reset-bruteforce-counter")
        void resetBruteForceCounter(@RequestBody String username);

        @GetMapping("/login-disabled/{username}")
        boolean loginDisabled(@PathVariable String username);

        @PostMapping("/password/reset")
        void resetPassword(@RequestParam("email") String email);

        @PostMapping("/password/change")
        void changePassword(@RequestParam("password") String password, @RequestParam("token") String token);

        @PostMapping("/update-user/{id}")
        void updateUser(@RequestBody UserModel user, @PathVariable Integer id);

        // Manga //

        @GetMapping("/manga/manga-list")
        List<MangaModel> getMangaList();

        @GetMapping("/manga/top-manga-list")
        List<MangaModel> getTopMangaList();

        @GetMapping("/manga/last-manga-list")
        List<MangaModel> getLastMangaList();

        @GetMapping("/manga/manga-details/{title}")
        MangaModel getMangaDetails(@PathVariable String title);

        @GetMapping("/manga/user-mangas/{username}")
        List<MangaModel> getUserMangas(@PathVariable String username);

        @PostMapping("/manga/add-manga")
        void addManga(@RequestBody MangaModel manga, @RequestParam("username") String username);

        @PostMapping("/manga/update-manga/{title}")
        void updateManga(@RequestBody MangaModel manga, @PathVariable String title);

        @DeleteMapping("/manga/delete-manga/{title}")
        void deleteManga(@PathVariable String title);

        // Anime //

        @GetMapping("/anime/anime-list")
        List<AnimeModel> getAnimeList();

        @GetMapping("/anime/top-anime-list")
        List<AnimeModel> getTopAnimeList();

        @GetMapping("/anime/last-anime-list")
        List<AnimeModel> getLastAnimeList();

        @GetMapping("/anime/anime-details/{title}")
        AnimeModel getAnimeDetails(@PathVariable String title);

        @GetMapping("/anime/user-animes/{username}")
        List<AnimeModel> getUserAnimes(@PathVariable String username);

        @PostMapping("/anime/add-anime")
        void addAnime(@RequestBody AnimeModel anime, @RequestParam("username") String username);

        @PostMapping("/anime/update-anime/{title}")
        void updateAnime(@RequestBody AnimeModel anime, @PathVariable String title);

        @DeleteMapping("/anime/delete-anime/{title}")
        void deleteAnime(@PathVariable String title);

        // Movie //

        @GetMapping("/movie/movie-list")
        List<MovieModel> getMovieList();

        @GetMapping("/movie/top-movie-list")
        List<MovieModel> getTopMovieList();

        @GetMapping("/movie/last-movie-list")
        List<MovieModel> getLastMovieList();

        @GetMapping("/movie/movie-details/{title}")
        MovieModel getMovieDetails(@PathVariable String title);

        @GetMapping("/movie/user-movies/{username}")
        List<MovieModel> getUserMovies(@PathVariable String username);

        @PostMapping("/movie/add-movie")
        void addMovie(@RequestBody MovieModel movie, @RequestParam("username") String username);

        @PostMapping("/movie/update-movie/{title}")
        void updateMovie(@RequestBody MovieModel movie, @PathVariable String title);

        @DeleteMapping("/movie/delete-movie/{title}")
        void deleteMovie(@PathVariable String title);

        // Enum //

        @GetMapping("/enum/types")
        List<String> getTypes();

        @GetMapping("/enum/status")
        List<String> getStatus();

        @GetMapping("/enum/genres")
        List<String> getGenres();

        @GetMapping("/enum/movie-types")
        List<String> getMovieTypes();

        @GetMapping("/enum/file-types")
        List<String> getFileTypes();

        // File //

        @PostMapping("/file/manga/{title}/add-file")
        void addMangaFile(@RequestBody FileModel file, @PathVariable String title);

        @PostMapping("/file/anime/{title}/add-file")
        void addAnimeFile(@RequestBody FileModel file, @PathVariable String title);

        @PostMapping("/file/movie/{title}/add-file")
        void addMovieFile(@RequestBody FileModel file, @PathVariable String title);

        @GetMapping("/file/{title}/manga-files")
        List<FileModel> getMangaFiles(@PathVariable String title);

        @GetMapping("/file/{title}/anime-files")
        List<FileModel> getAnimeFiles(@PathVariable String title);

        @GetMapping("/file/{title}/movie-files")
        List<FileModel> getMovieFiles(@PathVariable String title);

        @GetMapping("/file/file-details/{id}")
        FileModel getFileDetails(@PathVariable Integer id);

        @PostMapping("/file/update-file/{id}")
        void updateFile(@RequestBody FileModel file, @PathVariable Integer id);

        @DeleteMapping("/file/delete-file/{id}")
        void deleteFile(@PathVariable Integer id);

        // Review //

        @PostMapping("/review/manga/{title}/add-review")
        void addMangaReview(@RequestBody ReviewModel review, @RequestParam("username") String username,
                        @PathVariable String title);

        @PostMapping("/review/anime/{title}/add-review")
        void addAnimeReview(@RequestBody ReviewModel review, @RequestParam("username") String username,
                        @PathVariable String title);

        @PostMapping("/review/movie/{title}/add-review")
        void addMovieReview(@RequestBody ReviewModel review, @RequestParam("username") String username,
                        @PathVariable String title);

        @GetMapping("/review/{title}/manga-reviews")
        List<ReviewModel> getMangaReviews(@PathVariable String title);

        @GetMapping("/review/{title}/anime-reviews")
        List<ReviewModel> getAnimeReviews(@PathVariable String title);

        @GetMapping("/review/{title}/movie-reviews")
        List<ReviewModel> getMovieReviews(@PathVariable String title);

        @GetMapping("/review/review-details/{id}")
        ReviewModel getReviewDetails(@PathVariable Integer id);

        @PostMapping("/review/update-review/{id}")
        void updateReview(@RequestBody ReviewModel review, @PathVariable Integer id);

        @DeleteMapping("/review/delete-review/{id}")
        void deleteReview(@PathVariable Integer id);

        // Admin //
        @GetMapping("/admin/user-list")
        List<UserModel> getUserList();

        @GetMapping("admin/roles")
        List<RoleModel> getRoles();

        @GetMapping("admin/role-details/{nameRole}")
        RoleModel getRoleDetails(@PathVariable String nameRole);

        @PostMapping("/admin/{id}/add-role")
        void addRoleToUser(@RequestBody RoleModel role, @PathVariable Integer id);

        @PostMapping("/admin/account-locked/{id}")
        void accountLocked(@PathVariable Integer id);

        @PostMapping("/admin/account-non-locked/{id}")
        void accountNonLocked(@PathVariable Integer id);
}
