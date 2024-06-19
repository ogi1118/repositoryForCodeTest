import java.util.Scanner;
import java.util.ArrayList;

public class App {

    public static class Query {
        String queryType = "";
        String reserveNum = "";
        int dateOfReserve = 0;
        int numOfSection = 0;
        int numOfPeople = 0;
        String tableNum = "";
        String dateTimeOfQuery = "";
        int dateOfQuery = 0;
        String timeOfQuery = "";
    }

    public static void main(String[] args) {
        // get data
        String[] lines = getStdin();
        // for (int i = 0; i < lines.length; i++) {
        // System.out.println(lines[i]);
        // }
        int NumOfTables = Integer.parseInt(lines[0]);
        // System.out.println(NumOfTables);
        String[] tmpArr = lines[1].split(" ");
        int[] table = new int[NumOfTables];
        for (int i = 0; i < NumOfTables; i++) {
            table[i] = Integer.parseInt(tmpArr[i]);
            // System.out.println(table[i]);
        }
        tmpArr = lines[2].split(" ");
        String businessHours = tmpArr[0];
        // System.out.println(businessHours);
        int NumOfSections = Integer.parseInt(tmpArr[1]);
        String[] sections = new String[NumOfSections];
        for (int i = 0; i < NumOfSections; i++) {
            sections[i] = tmpArr[i + 2];
            // System.out.println(sections[i]);
        }
        // get queries
        // System.out.println("get queries");
        String[] queriesData = getQueries(lines);
        // System.out.println("here");
        ArrayList<Query> queries = new ArrayList<>();
        for (int i = 0; i < queriesData.length; i++) {
            Query query = new Query();
            String[] tmp = new String[8];
            tmp = queriesData[i].split(" ");
            System.out.println(i);
            query.dateOfQuery = Integer.parseInt(tmp[0]);
            System.out.println("date: " + tmp[0]);
            System.out.println("time: " + tmp[1]);
            query.timeOfQuery = tmp[1];
            query.queryType = tmp[2];
            if (query.queryType.equals("time")) {
                // その時点でのリストを全部探す、DHHMMが一致したら表示(ソート)
                System.out.println("time query");
                System.out.println(tmp[3]);
                query.numOfSection = Integer.parseInt(tmp[3]);
                System.out.println(queries.size());
                for (int j = 0; j < queries.size(); j++) {
                    String txtToShow = "";
                    System.out.println(query.dateOfQuery);
                    System.out.println(queries.get(j).dateOfQuery);
                    System.out.println(query.numOfSection);
                    System.out.println(queries.get(j).dateOfQuery);
                    if (query.dateOfQuery == queries.get(j).dateOfQuery
                            && queries.get(j).numOfSection == query.numOfSection) {
                        txtToShow += String.valueOf(queries.get(j).dateOfQuery) + " " + queries.get(j).timeOfQuery + " "
                                + queries.get(j).tableNum + " = "
                                + queries.get(j).reserveNum;
                        // System.out.print(String.valueOf(queries.get(j).dateOfQuery));
                        // System.out.println(" " + queries.get(j).timeOfQuery + " " +
                        // queries.get(j).tableNum + " = "
                        // + queries.get(j).reserveNum);
                        System.out.println(txtToShow);
                    }
                }
                queries.add(query);
                System.out.println("finished");
            } else if (query.queryType.equals("issue-specified")) {
                System.out.println("issue-specified query");
                query.reserveNum = tmp[3];
                query.dateOfReserve = Integer.parseInt(tmp[4]);
                query.numOfSection = Integer.parseInt(tmp[5]);
                query.numOfPeople = Integer.parseInt(tmp[6]);
                query.tableNum = tmp[7];
                System.out.println("date:" + tmp[4]);
                System.out.println("numOfSection:" + tmp[5]);
                // if()
                queries.add(query);
                System.out.println("finished");
            }
        }

        System.out.println("program done");

    }

    private static String[] getQueries(String[] lines) {
        ArrayList<String> queries = new ArrayList<String>();
        // System.out.println(lines.length);
        // System.out.println(lines[3]);
        String[] ret = new String[lines.length - 3];
        int i = 0;
        for (int l = 3; l < lines.length; l++) {
            ret[i] = lines[l];
            // System.out.println(ret[i]);
            i++;
        }
        // System.out.println("for end");
        return ret;
    }

    private static String[] getStdin() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            lines.add(line);
        }
        return lines.toArray(new String[lines.size()]);
    }
}