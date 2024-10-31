package uz.muhammadtrying.rock_paper_scissors.service;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.rock_paper_scissors.entity.Game;
import uz.muhammadtrying.rock_paper_scissors.entity.GameStat;
import uz.muhammadtrying.rock_paper_scissors.entity.User;
import uz.muhammadtrying.rock_paper_scissors.repo.GameRepository;
import uz.muhammadtrying.rock_paper_scissors.repo.GameStatRepository;
import uz.muhammadtrying.rock_paper_scissors.repo.UserRepository;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GameStatRepository gameStatRepository;

    public MainServiceImpl(GameRepository gameRepository,
                           UserRepository userRepository,
                           GameStatRepository gameStatRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.gameStatRepository = gameStatRepository;
    }

    @Override
    public List<Game> fetchAllGame() {
        return gameRepository.findAll();
    }

    @Override
    public Game saveGame(String name) {
        return gameRepository.save(Game.builder()
                .name(name)
                .build());
    }

    @Override
    public void startGame(Integer id) {
        Game game = gameRepository.findById(id).get();
        game.setActive(true);
        gameRepository.save(game);
    }

    @Override
    public Game findGameById(Integer id) {
        return gameRepository.findById(id).get();
    }

    @Override
    public User saveUser(String nickname) {
        return userRepository.save(User.builder().nickname(nickname).build());
    }

    @Override
    public List<User> findAllUsersInGame() {
        return userRepository.findAll();
    }

    @Override
    public void saveGameResult(GameStat gameStat) {
        gameStatRepository.save(gameStat);
    }

    @Override
    public List<GameStat> fetchAllByGameIdOrderByScores(Integer gameId) {
        return gameStatRepository.findAllByGameIdOrderByScoreDescDuration(gameId);
    }

    @Override
    public void deleteGameById(Integer id) {
        gameRepository.deleteById(id);
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }
}
