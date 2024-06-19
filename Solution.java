class Solution {
    public int solution(boolean[][] A) {
        int n = A.length;
        int m = A[0].length;
        int maxArea = -1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 2; k <= Math.min(n - i, m - j) && k * k < n * m / 2; k++) {
                    int area = dfs(A, i, j, k, n, m); 
                    maxArea = Math.max(maxArea, area);
                }
            }
        }

        return maxArea;
    }

    private int dfs(boolean[][] A, int x, int y, int side, int n, int m) {
        if (!checkPossibility(A, x, y, side, n, m)) {
            return 0;
        }

        int area = side * side;

        for (int i = x + 1; i < n; i++) {
            for (int j = y + 1; j < m; j++) {
                if (dfs2(A, x, y, side, n, m)) {
                    return area;
                }
            }
        }

        return 0;
    }

    private boolean dfs2(boolean[][] A, int x, int y, int side, int n, int m) {
        if (!checkPossibility(A, x, y, side, n, m)) {
            return false;
        }

        return true;
    }

    private boolean checkPossibility(boolean[][] A, int x, int y, int side, int n, int m) {
        if (x + side > n || y + side > m) {
            return false;
        }

        for (int i = x; i < x + side; i++) {
            for (int j = y; j < y + side; j++) {
                if (!A[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }
}
