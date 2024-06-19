import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class rakusuru {

    public static void main(String[] args) {
        // このコードは標準入力と標準出力を用いたサンプルコードです。
        // このコードは好きなように編集・削除してもらって構いません。
        // ---
        // This is a sample code to use stdin and stdout.
        // Edit and remove this code as you like.

        String[] lines = getStdin();
        String[] tmp = lines[0].split(" ");
        int numOfData = Integer.parseInt(tmp[0]);
        int numOfQuery = Integer.parseInt(tmp[1]);
        int[][] scores = new int[numOfData][2];
        for (int i = 0; i < numOfData; i++) {
            String[] tmp2 = lines[i + 1].split(" ");
            scores[i][0] = Integer.parseInt(tmp2[0]);
            scores[i][1] = Integer.parseInt(tmp2[1]);
        }

        int lineIndex = numOfData + 1;
        for (int i = 0; i < numOfQuery; i++) {
            System.out.print("lineIndex=");
            System.out.println(lineIndex);
            String[] tmp3 = lines[lineIndex++].split(" ");
            int queryType = Integer.parseInt(tmp3[0]);
            if (queryType == 2) {
                int m = Integer.parseInt(tmp3[1]);
                System.out.println(calculatePossibleTopMCount(numOfData, m, scores));
            } else if (queryType == 1) {
                int playerNum = Integer.parseInt(tmp3[1]) - 1; // インデックスは0から始まるので-1する
                int min = Integer.parseInt(tmp3[2]);
                int max = Integer.parseInt(tmp3[3]);
                scores[playerNum][0] = max; // 最大値を更新
                scores[playerNum][1] = min; // 最小値を更新
            }
        }
    }

    private static String[] getStdin() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        return lines.toArray(new String[lines.size()]);
    }

    public static int calculatePossibleTopMCount(int N, int M, int[][] scores) {
        // 各人の最高点を収集
        int[] maxScores = new int[N];
        for (int i = 0; i < N; i++) {
            maxScores[i] = scores[i][0];
        }

        // 最高点のリストを降順にソート
        Arrays.sort(maxScores);
        reverse(maxScores);

        // 上位M人のボーダーラインとなる値を取得
        int borderValue = maxScores[Math.min(M, N) - 1];

        // 各人の最低点を収集
        int[] minScores = new int[N];
        for (int i = 0; i < N; i++) {
            minScores[i] = scores[i][1];
        }

        // 上位M人に入る可能性のある人数を計算
        int count = 0;
        for (int i = 0; i < N; i++) {
            int possibleMaxScore = scores[i][0];
            // 他の人が最低点を取る場合の得点を計算
            int[] potentialScores = new int[N];
            potentialScores[i] = possibleMaxScore; // 自分は最高点を取る
            for (int j = 0; j < N; j++) {
                if (i != j) {
                    potentialScores[j] = scores[j][1]; // 他の人は最低点を取る
                }
            }
            // ポテンシャルスコアを降順にソート
            Arrays.sort(potentialScores);
            reverse(potentialScores);
            // 自分が上位M人に入るかを確認
            if (potentialScores[Math.min(M, N) - 1] <= possibleMaxScore) {
                count++;
            }
        }

        return count;
    }

    // 配列を降順に並べ替えるヘルパーメソッド
    public static void reverse(int[] array) {
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }
}
