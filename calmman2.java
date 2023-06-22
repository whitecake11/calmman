#include <iostream>
#include <vector>

using namespace std;

const int WIDTH = 10;
const int HEIGHT = 20;

// 테트리스 블록 클래스
class Tetromino {
public:
    vector<vector<int>> shape;
    int x, y;

    Tetromino(vector<vector<int>> s) : shape(s), x(0), y(0) {}

    void moveLeft() {
        x--;
    }

    void moveRight() {
        x++;
    }

    void moveDown() {
        y++;
    }

    void rotate() {
        vector<vector<int>> rotated(shape[0].size(), vector<int>(shape.size()));
        for (int i = 0; i < shape.size(); i++) {
            for (int j = 0; j < shape[i].size(); j++) {
                rotated[j][shape.size() - 1 - i] = shape[i][j];
            }
        }
        shape = rotated;
    }
};

// 게임 보드 클래스
class Board {
public:
    vector<vector<int>> grid;

    Board() {
        grid.resize(HEIGHT, vector<int>(WIDTH, 0));
    }

    void printBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                cout << (grid[i][j] == 0 ? " " : "#") << " ";
            }
            cout << endl;
        }
    }

    bool isCollision(Tetromino tetromino) {
        for (int i = 0; i < tetromino.shape.size(); i++) {
            for (int j = 0; j < tetromino.shape[i].size(); j++) {
                if (tetromino.shape[i][j] != 0 && (tetromino.y + i >= HEIGHT || tetromino.x + j < 0 || tetromino.x + j >= WIDTH || grid[tetromino.y + i][tetromino.x + j] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    void mergeTetromino(Tetromino tetromino) {
        for (int i = 0; i < tetromino.shape.size(); i++) {
            for (int j = 0; j < tetromino.shape[i].size(); j++) {
                if (tetromino.shape[i][j] != 0) {
                    grid[tetromino.y + i][tetromino.x + j] = tetromino.shape[i][j];
                }
            }
        }
    }

    void clearLines() {
        for (int i = HEIGHT - 1; i >= 0; i--) {
            bool lineComplete = true;
            for (int j = 0; j < WIDTH; j++) {
                if (grid[i][j] == 0) {
                    lineComplete = false;
                    break;
                }
            }
            if (lineComplete) {
                grid.erase(grid.begin() + i);
                grid.insert(grid.begin(), vector<int>(WIDTH, 0));
            }
        }
    }
};

int main() {
    Board board;
    vector<vector<int>> shape = {
        {1, 1, 1, 1}
    };
    Tetromino tetromino(shape);

    while (true) {
        system("cls");
        board.printBoard();

        char input;
        cout << "Enter a command: ";
        cin >> input;

        if (input == 'a') {
            Tetromino temp = tetromino;
            temp.moveLeft();
            if (!board.isCollision(temp)) {
                tetromino.moveLeft();
            }
        } else if (input == 'd') {
            Tetromino temp = tetromino;
            temp.moveRight();
            if (!board.isCollision(temp)) {
                tetromino.moveRight();
            }
        } else if (input == 's') {
            Tetromino temp = tetromino;
            temp.moveDown();
            if (!board.isCollision(temp)) {
                tetromino.moveDown();
            } else {
                board.mergeTetromino(tetromino);
                board.clearLines();
                tetromino = Tetromino(shape);
            }
        } else if (input == 'r') {
            Tetromino temp = tetromino;
            temp.rotate();
            if (!board.isCollision(temp)) {
                tetromino.rotate();
            }
        }
    }

    return 0;
}