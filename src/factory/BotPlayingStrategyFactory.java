package factory;

import model.BotDifficultyLevel;
import strategy.botplayingstrategy.BotPlayingStrategy;
import strategy.botplayingstrategy.EasyPlayingStrategy;
import strategy.botplayingstrategy.HardLevelStrategy;
import strategy.botplayingstrategy.MediumLevelStrategy;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategy(BotDifficultyLevel botDifficultyLevel){
        return switch (botDifficultyLevel){
            case EASY -> new EasyPlayingStrategy();
            case MEDIUM -> new MediumLevelStrategy();
            case HARD -> new HardLevelStrategy();
        };
    }
}
