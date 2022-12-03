package ca.nickknack.day2;

public class Round {
    private Move playerMove;
    private Move opponentMove;
    private Score playerScore;

    private static final int WIN_SCORE = 6;
    private static final int DRAW_SCORE = 3;
    private static final int LOSE_SCORE = 0;

    public Round(Move playerMove, Move opponentMove) {
        this.playerMove = playerMove;
        this.opponentMove = opponentMove;
        setScore(playerMove, opponentMove);
    }

    public Round(Move opponentMove, Score score) {
        this.opponentMove = opponentMove;
        this.playerScore = score;
        setPlayerMove(opponentMove, score);
    }

    public int getPlayerScore() {
        return this.playerScore.modifier + this.playerMove.modifier;
    }

    private void setScore(Move playerMove, Move opponentMove) {
        if (playerMove.beats(opponentMove)) {
            this.playerScore = Score.WIN;
        } else if (opponentMove.beats(playerMove)) {
            this.playerScore = Score.LOSE;
        } else {
            this.playerScore = Score.DRAW;
        }
    }

    private void setPlayerMove(Move opponentMove, Score score) {
        if (Score.WIN == score) {
            this.playerMove = Move.getWinningMove(opponentMove);
        } else if (Score.LOSE == score) {
            this.playerMove = Move.getLosingMove(opponentMove);
        } else {
            this.playerMove = opponentMove;
        }
    }
}
