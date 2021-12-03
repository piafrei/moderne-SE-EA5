import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int NUMBER_OF_FIELDS = 9;
    private static final int MAX_FIELD_VALUE_X = 2;
    private static final int MAX_FIELD_VALUE_Y = 2;
    private static final String USER_SIGN_CIRCLE = "O";
    private static final String USER_SIGN_CROSS = "X";
    private static final int FIRST_ROW_FIRST_COLUMN = 0;
    private static final int FIRST_ROW_SECOND_COLUMN = 1;
    private static final int FIRST_ROW_THIRD_COLUMN = 2;
    private static final int SECOND_ROW_FIRST_COLUMN = 3;
    private static final int SECOND_ROW_SECOND_COLUMN = 4;
    private static final int SECOND_ROW_THIRD_COLUMN = 5;
    private static final int THIRD_ROW_FIRST_COLUMN = 6;
    private static final int THIRD_ROW_SECOND_COLUMN = 7;
    private static final int THIRD_ROW_THIRD_COLUMN = 8;
    public static final int PLAYER_ONE_NUMBER = 1;
    public static final int PLAYER_TWO_NUMBER = 2;
    public static final int MIN_FIELD_VALUE_X = 0;
    public static final int MIN_FIELD_VALUE_Y = 0;


    public static void main(String[] args) {
        Player playerOne = new Player(PLAYER_ONE_NUMBER);
        Player playerTwo = new Player(PLAYER_TWO_NUMBER);
        boolean gameFinished = false;
        Player activePlayer;
        String winnerSign;
        int choosenFieldId;
        Field[] gameFields = initialiseGameFields();

        printStatementToConsole("TicTacToe Spiel wird gestartet! Das Spielfeld baut sich wie folgt auf:");
        printEmptyGameFieldsWithFieldIds();
        Scanner userInputReader = new Scanner(System.in);

        initialisePlayer(playerOne, USER_SIGN_CIRCLE, userInputReader);
        initialisePlayer(playerTwo, USER_SIGN_CROSS, userInputReader);
        activePlayer = chooseActivePlayer(playerOne, playerTwo);

        while (!gameFinished) {
            printStatementToConsole(activePlayer.getName() + " bitte wähle ein Feld (Zahl zwischen 0 und " + getMaxFieldId() + ")");
            try {
                choosenFieldId = userInputReader.nextInt();
                if (choosenFieldIdIsInValidRange(choosenFieldId)) {
                    if (gameFieldIsNotFilled(gameFields[choosenFieldId])) {
                        gameFields[choosenFieldId].value = activePlayer.getSign();
                        printGameFields(gameFields);
                        winnerSign = checkIfWinnerExists(gameFields);
                        if (!winnerSign.isEmpty()) {
                            printGameEndedMessage(playerOne, playerTwo, winnerSign);
                            gameFinished = true;
                            continue;
                        }
                        activePlayer = toggleActivePlayer(playerOne, playerTwo, activePlayer);
                    } else {
                        printStatementToConsole("Das gewählte Feld ist bereits belegt. Bitte wähle ein anderes Feld");
                        continue;
                    }
                } else {
                    printStatementToConsole("Ungültige Eingabe. Bitte wähle eine valide Field Id zwischen 0 und " + getMaxFieldId() + ")");
                    continue;
                }
            } catch (InputMismatchException inputException) {
                printStatementToConsole("Ungültige Eingabe. Bitte wähle eine valide Field Id zwischen 0 und " + getMaxFieldId() + ")");
                continue;
            }

        }
    }

    private static void printGameEndedMessage(Player playerOne, Player playerTwo, String winnerSign) {
        if (winnerSign.equals("unentschieden")) {
            printStatementToConsole("Spiel beendet. Leider ein unentschieden!");
        } else {
            if (winnerSign.equals(playerOne.getSign())) {
                printStatementToConsole("Spiel beendet. Herzlichen Glückwunsch " + playerOne.getName() + "!");
            } else {
                printStatementToConsole("Spiel beendet. Herzlichen Glückwunsch " + playerTwo.getName() + "!");
            }
        }
    }

    private static Player chooseActivePlayer(Player playerOne, Player playerTwo) {
        Player activePlayer;Random rand = new Random();
        int starter = rand.nextInt(1);

        switch (starter) {
            case 0:
                activePlayer = playerOne;
                break;
            case 1:
                activePlayer = playerTwo;
                break;
            default:
                activePlayer = playerOne;
        }
        return activePlayer;
    }

    private static int getMaxFieldId() {
        return NUMBER_OF_FIELDS - 1;
    }

    private static boolean gameFieldIsNotFilled(Field gameField) {
        return !gameFieldIsFilled(gameField);
    }

    private static String checkIfWinnerExists(Field[] gameFields) {
        String winner = "";
        winner = checkWinnerInHorizontalLine(gameFields, winner);
        winner = checkWinnerInVerticalLine(gameFields, winner);
        winner = checkWinnerInDiagonalLine(gameFields, winner);
        return winner;
    }

    private static String checkWinnerInDiagonalLine(Field[] gameFields, String winner) {
        if (threeFieldsHaveSameValue(gameFields[FIRST_ROW_FIRST_COLUMN], gameFields[SECOND_ROW_SECOND_COLUMN], gameFields[THIRD_ROW_THIRD_COLUMN])) {
            winner = determineWinner(gameFields[FIRST_ROW_FIRST_COLUMN].value, winner);
        }

        if (threeFieldsHaveSameValue(gameFields[FIRST_ROW_THIRD_COLUMN], gameFields[SECOND_ROW_SECOND_COLUMN], gameFields[THIRD_ROW_FIRST_COLUMN])) {
            winner = determineWinner(gameFields[FIRST_ROW_THIRD_COLUMN].value, winner);
        }
        return winner;
    }

    private static String checkWinnerInVerticalLine(Field[] gameFields, String winner) {
        if (threeFieldsHaveSameValue(gameFields[FIRST_ROW_FIRST_COLUMN], gameFields[SECOND_ROW_FIRST_COLUMN], gameFields[THIRD_ROW_FIRST_COLUMN])) {
            winner = determineWinner(gameFields[FIRST_ROW_FIRST_COLUMN].value, winner);
        }

        if (threeFieldsHaveSameValue(gameFields[FIRST_ROW_SECOND_COLUMN], gameFields[SECOND_ROW_SECOND_COLUMN], gameFields[THIRD_ROW_SECOND_COLUMN])) {
            winner = determineWinner(gameFields[FIRST_ROW_SECOND_COLUMN].value, winner);
        }

        if (threeFieldsHaveSameValue(gameFields[FIRST_ROW_THIRD_COLUMN], gameFields[SECOND_ROW_THIRD_COLUMN], gameFields[THIRD_ROW_THIRD_COLUMN])) {
            winner = determineWinner(gameFields[FIRST_ROW_THIRD_COLUMN].value, winner);
        }
        return winner;
    }

    private static String checkWinnerInHorizontalLine(Field[] gameFields, String winner) {
        if (threeFieldsHaveSameValue(gameFields[FIRST_ROW_FIRST_COLUMN], gameFields[FIRST_ROW_SECOND_COLUMN], gameFields[FIRST_ROW_THIRD_COLUMN])) {
            winner = determineWinner(gameFields[FIRST_ROW_FIRST_COLUMN].value, winner);
        }

        if (threeFieldsHaveSameValue(gameFields[SECOND_ROW_FIRST_COLUMN], gameFields[SECOND_ROW_SECOND_COLUMN], gameFields[SECOND_ROW_THIRD_COLUMN])) {
            winner = determineWinner(gameFields[SECOND_ROW_FIRST_COLUMN].value, winner);
        }

        if (threeFieldsHaveSameValue(gameFields[FIRST_ROW_FIRST_COLUMN], gameFields[THIRD_ROW_SECOND_COLUMN], gameFields[THIRD_ROW_THIRD_COLUMN])) {
            winner = determineWinner(gameFields[FIRST_ROW_FIRST_COLUMN].value, winner);
        }
        return winner;
    }

    private static String determineWinner(String winnerFieldValue, String winner) {
        if (winner.isEmpty()) {
            winner = winnerFieldValue;
        } else {
            winner = "unentschieden";
        }
        return winner;
    }

    private static boolean threeFieldsHaveSameValue(Field firstField, Field secondField, Field thirdField) {
        return gameFieldIsFilled(firstField) && gameFieldIsFilled(secondField) && gameFieldIsFilled(thirdField) && fieldsAreEqualFilled(firstField, secondField, thirdField);
    }

    private static boolean fieldsAreEqualFilled(Field firstField, Field secondField, Field thirdField) {
        return firstField.getValue().equals(secondField.getValue()) && secondField.getValue().equals(thirdField.getValue());
    }

    private static boolean gameFieldIsFilled(Field gameField) {
        return gameField.getValue() != null && !gameField.getValue().isEmpty();
    }

    private static Player toggleActivePlayer(Player playerOne, Player playerTwo, Player activePlayer) {
        if (activePlayer == playerOne) {
            activePlayer = playerTwo;
        } else {
            activePlayer = playerOne;
        }
        return activePlayer;
    }

    private static void printGameFields(Field[] gameFields) {
        printStatementToConsole(gameFields[FIRST_ROW_FIRST_COLUMN].getValue() + "|" + gameFields[FIRST_ROW_SECOND_COLUMN].getValue() + "|" + gameFields[FIRST_ROW_THIRD_COLUMN].getValue());
        printStatementToConsole(gameFields[SECOND_ROW_FIRST_COLUMN].getValue() + "|" + gameFields[SECOND_ROW_SECOND_COLUMN].getValue() + "|" + gameFields[SECOND_ROW_THIRD_COLUMN].getValue());
        printStatementToConsole(gameFields[THIRD_ROW_FIRST_COLUMN].getValue() + "|" + gameFields[THIRD_ROW_SECOND_COLUMN].getValue() + "|" + gameFields[THIRD_ROW_THIRD_COLUMN].getValue());
    }

    private static void printEmptyGameFieldsWithFieldIds() {
        printStatementToConsole("0|1|2");
        printStatementToConsole("3|4|5");
        printStatementToConsole("6|7|8");
    }

    private static Field[] initialiseGameFields() {
        Field[] fields = new Field[NUMBER_OF_FIELDS];
        int fieldId = 0;
        for (int x = MIN_FIELD_VALUE_X; x <= MAX_FIELD_VALUE_X; x++) {
            for (int y = MIN_FIELD_VALUE_Y; y <= MAX_FIELD_VALUE_Y; y++) {
                Field field = new Field(x, y);
                fields[fieldId] = field;
                fieldId++;
            }
        }
        return fields;
    }

    private static boolean choosenFieldIdIsInValidRange(int choosenFieldId) {
        return choosenFieldId <= 8 && choosenFieldId >= 0;
    }

    private static void initialisePlayer(Player player, String userSign, Scanner userInputReader) {
        String userNameInput;
        player.setSign(userSign);
        printStatementToConsole("Bitte geben Sie den Namen des Spieler " + player.getNumber() + " ein:");
        userNameInput = getUserInput(userInputReader);
        initialiseNameForPlayer(player, userNameInput);
    }

    private static void initialiseNameForPlayer(Player player, String userNameInput) {
        if (inputWasProvided(userNameInput)) {
            player.setName(userNameInput);
            printStatementToConsole("Willkommen: " + player.getName() + "!");
        }
    }

    private static void printStatementToConsole(String s) {
        System.out.println(s);
    }

    private static boolean inputWasProvided(String userInput) {
        return userInput != null && !userInput.isEmpty();
    }

    private static String getUserInput(Scanner userInput) {
        return userInput.next();
    }
}
