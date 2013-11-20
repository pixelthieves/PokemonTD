package com.pixelthieves.pokemontd.graphics.ui.menu;

import com.badlogic.gdx.math.Rectangle;
import com.pixelthieves.pokemontd.App;
import com.pixelthieves.pokemontd.GameService;
import com.pixelthieves.pokemontd.graphics.ui.Button;
import com.pixelthieves.pokemontd.graphics.ui.Gui;
import com.pixelthieves.pokemontd.graphics.ui.InteractiveBlockParent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas on 10/8/13.
 */
public class Menu extends Gui {

    private final LeaderboardTab leaderboard;
    private Button menu;

    public void setMenu(Button menu) {
        this.menu = menu;
    }

    public GameService getGameSevice() {
        return app.getGameSevice();
    }

    public enum Type {
        BLANK, INGAME, MAIN, END, LEADERBOARD;
    }

    private final MenuTab signInBox;
    private final MenuTab menuBox;
    private final MenuTab inGameMenu;
    private final MenuTab endGame;
    private final MenuTab defaultCard;
    private MenuTab pickedCard;
    private final List<MenuTab> guiDialogRoots = new ArrayList<MenuTab>();

    public Menu(App app) {
        super(app);
        int height = (int) (squareSize * 1.5f);
        int menuWidth = height / 3 * 4;
        Rectangle rectangle = getRectangle(menuWidth, height);
        signInBox = new SignInBox(this, rectangle, 5);
        inGameMenu = new InGameMenu(this, rectangle, 5);
        menuBox = new MenuBox(this, rectangle, 5);
        endGame = new EndGame(this, rectangle, 5);
        leaderboard = new LeaderboardTab(this, getRectangle(menuWidth, (int) (height * 1.5f)), 7);

        guiDialogRoots.add(signInBox);
        guiDialogRoots.add(inGameMenu);
        guiDialogRoots.add(menuBox);
        guiDialogRoots.add(endGame);

        defaultCard = signInBox;
        pickedCard = defaultCard;
    }

    private Rectangle getRectangle(int width, int height) {
        return new Rectangle(center.x - width / 2, center.y - height / 2, width, height);
    }

    @Override
    public void render() {
        if (isVisible()) {
            super.render();
            pickedCard.render();
        }
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        InteractiveBlockParent cardBeforeClick = pickedCard;
        if (menu.hit(x, y)) {
            return true;
        }

        if (isVisible()) {
            if (!pickedCard.hit(x, y) && pickedCard.isCloseTabWhenNotClicked()) {
                pickedCard = null;
            }
        }
        return cardBeforeClick != null;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        for (MenuTab menu : guiDialogRoots) {
            menu.pan(x, y, deltaX, deltaY);
        }
        return isVisible();
    }

    public boolean isVisible() {
        return pickedCard != null;
    }

    public void triggerMenu(Type type) {
        this.pickedCard = getPickedCardByType(type);
    }


    private MenuTab getPickedCardByType(Type type) {
        switch (type) {
            case INGAME:
                return inGameMenu;
            case MAIN:
                return menuBox;
            case END:
                return endGame;
            case LEADERBOARD:
                return leaderboard;
            default:
                return null;
        }
    }


    void switchCard(Type type) {
        switchCard(getPickedCardByType(type));
    }

    void switchCard(MenuTab card) {
        pickedCard = card;
    }

}