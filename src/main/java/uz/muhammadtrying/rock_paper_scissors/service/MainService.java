package uz.muhammadtrying.rock_paper_scissors.service;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.rock_paper_scissors.entity.Game;
import uz.muhammadtrying.rock_paper_scissors.entity.GameStat;
import uz.muhammadtrying.rock_paper_scissors.entity.User;

import java.util.List;

@Service
public interface MainService {
    List<Game> fetchAllGame();

    Game saveGame(String name);

    void startGame(Integer id);

    Game findGameById(Integer id);

    User saveUser(String nickname);

    List<User> findAllUsersInGame();

    void saveGameResult(GameStat gameStat);

    List<GameStat> fetchAllByGameIdOrderByScores(Integer gameId);

    void deleteGameById(Integer id);

    void deleteUserById(Integer id);
}
