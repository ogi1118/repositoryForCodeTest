import java.util.*;

// 20221-1-1は土曜日

public class Shipping
 {
    static class Box
    static class Query {
        String kind_of_query;// クエリの種類(ORDER, SHIP, CANCEL)
        String datetime;// クエリの発行された日時
        String order_num;// 注文番号
        String send_date;// 発送日
        int num_of_kinds;// 注文する商品の種類数
        String[] goods_numbers_of_order;// 注文商品の商品番号
        int[] quantity_of_order;// 注文個数
        String text_to_show = "";// 表示するテキスト
        boolean is_accept = false;// 注文が受け入れられたかどうか
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String[] input = getStdin(scanner);
        String[] info = input[0].split(" ");

        int goods_num = Integer.parseInt(info[0]);
        int max_order = Integer.parseInt(info[1]);
        int max_days = Integer.parseInt(info[2]);
        String[] goods = input[1].split(" ");

        String[] tmp_str = input[2].split(" ");
        int[] flag_of_days = generateYearArray(tmp_str, max_order);
        int query_num = Integer.parseInt(input[3]);
        System.gc();
        // System.out.println("finished init");
        ArrayList<Query> queries = getQueryStrings(query_num, flag_of_days, scanner);
        for (int i = 0; i < queries.size(); i++) {
            System.out.println(queries.get(i).text_to_show);
        }
        System.out.println();
    }

    public static int[] generateYearArray(String[] weekdayArray, int MAX) {
        int[] yearArray = new int[365]; // 365日分の配列を生成

        // 各日にちの値を設定
        for (int i = 0; i < yearArray.length; i++) {
            int weekdayIndex = i % 7; // 曜日を繰り返すためのインデックス
            if (weekdayIndex < weekdayArray.length && weekdayArray[weekdayIndex].equals("1")) {
                yearArray[i] = MAX; // "1"の場合はMAXを設定
            } else {
                yearArray[i] = 0; // "0"の場合は0を設定
            }
        }

        return yearArray;
    }

    private static String[] getStdin(Scanner scanner) {
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            lines.add(scanner.nextLine());
        }
        return lines.toArray(new String[lines.size()]);
    }

    public static int dateToIndex(String dateStr) {
        // 引数の日付文字列を年、月、日に分割
        String[] parts = dateStr.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        // 2022年1月1日からの日数を計算して返す
        int days_since = (year - 2022) * 365 + countDaysOfMonth(month) + day - 1;
        return days_since;
    }

    public static int countDaysOfMonth(int month) {
        int[] daysInMonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30 }; // 各月の日数
        int days = 0;
        for (int i = 1; i < month; i++) {
            days += daysInMonth[i];
        }
        return days;
    }

    private static ArrayList<Query> getQueryStrings(int query_num, int[] flag_of_days, Scanner scanner) {
        ArrayList<Query> queryStrings = new ArrayList<Query>();
        int[] flag_copy = Arrays.copyOf(flag_of_days, flag_of_days.length);
        for (int i = 0; i < query_num; i++) {
            Query query = new Query();
            String tmp[] = new String[2];
            tmp = scanner.nextLine().split(" ");
            query.kind_of_query = tmp[0];
            query.datetime = tmp[1];
            if (query.kind_of_query.equals("ORDER")) {
                String[] tmp_str = scanner.nextLine().split(" ");
                query.order_num = tmp_str[0];
                query.send_date = tmp_str[1];
                query.num_of_kinds = Integer.parseInt(tmp_str[2]);
                query.goods_numbers_of_order = new String[query.num_of_kinds];
                query.quantity_of_order = new int[query.num_of_kinds];
                for (int j = 0; j < query.num_of_kinds; j++) {
                    tmp_str = scanner.nextLine().split(" ");
                    query.goods_numbers_of_order[j] = tmp_str[0];
                    query.quantity_of_order[j] = Integer.parseInt(tmp_str[1]);
                    // System.out.print("注文番号" + query.order_num + "商品番号" + query.goods_numbers_of_order[j] + " * " +
                    //         query.quantity_of_order[j]
                    //         + "date = " + query.send_date + " index = " + dateToIndex(query.send_date)
                    //         + " flag = " + flag_copy[dateToIndex(query.send_date)]);
                    flag_copy[dateToIndex(query.send_date)] -= query.quantity_of_order[j];
                    if (flag_copy[dateToIndex(query.send_date)] <= 0) {
                        query.text_to_show = query.datetime + " Ordered " + query.order_num
                                + " Error: the number of available shipments has been exceeded.";
                        flag_copy[dateToIndex(query.send_date)] += query.quantity_of_order[j];
                        // System.out.println("-> error" + flag_copy[dateToIndex(query.send_date)]);
                        break;
                    } else {
                        // System.out.println("->" + flag_copy[dateToIndex(query.send_date)]);
                        query.text_to_show = query.datetime + " Ordered " + query.order_num;
                        query.is_accept = true;
                    }
                }

                queryStrings.add(query);
            } else if (query.kind_of_query.equals("CANCEL")) {
                query.num_of_kinds = 1;
                query.order_num = scanner.nextLine();
                query.text_to_show = query.datetime + " Canceled " + query.order_num;
                for (int j = 0; j < queryStrings.size(); j++) {
                    // System.out.println(queryStrings.get(j).order_num + " " + query.order_num);
                    if (queryStrings.get(j).kind_of_query.equals("ORDER")
                            && queryStrings.get(j).order_num.equals(query.order_num)) {
                        queryStrings.get(j).is_accept = false;
                        for (int k = 0; k < queryStrings.get(j).quantity_of_order.length; k++) {
                            flag_copy[get_day(
                                    queryStrings.get(j).send_date)] += queryStrings.get(j).quantity_of_order[k];
                        }
                    }
                }
                queryStrings.add(query);
            } else if (query.kind_of_query.equals("SHIP")) {
                query.num_of_kinds = 1;
                String[] tmp_str = query.datetime.split("T");
                String ship_date = tmp_str[0];
                List<String> ship_list = new ArrayList<String>();
                int count = 0;
                for (int k = 0; k < queryStrings.size(); k++) {
                    // System.out.println(queryStrings.get(k).send_date + " " + ship_date);
                    if (queryStrings.get(k).kind_of_query.equals("ORDER")
                            && queryStrings.get(k).send_date.equals(ship_date)
                            && queryStrings.get(k).is_accept == true) {
                        count++;
                        ship_list.add(queryStrings.get(k).order_num);
                    }
                }
                for (int k = 0; k < ship_list.size(); k++) {
                    query.text_to_show += ship_list.get(k);
                    if (k != ship_list.size() - 1) {
                        query.text_to_show += " ";
                    }
                }
                tmp_str = query.text_to_show.split(" ");
                Arrays.sort(tmp_str);
                query.text_to_show = query.datetime + " Shipped " + ship_list.size() + " Orders\n";
                for (int k = 0; k < tmp_str.length; k++) {
                    query.text_to_show += tmp_str[k];
                    if (k != tmp_str.length - 1) {
                        query.text_to_show += " ";
                    }
                }
                queryStrings.add(query);
            }
        }
        return queryStrings;
    }

    private static int get_day(String date_string) {
        String[] tmp = date_string.split("-");
        int year = Integer.parseInt(tmp[0]);
        int month = Integer.parseInt(tmp[1]);
        int date = Integer.parseInt(tmp[2]);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }
}