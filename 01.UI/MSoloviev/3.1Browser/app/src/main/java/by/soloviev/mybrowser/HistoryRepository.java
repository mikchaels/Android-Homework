package by.soloviev.mybrowser;


import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {

    private static HistoryRepository sHistoryRepository = new HistoryRepository();

    private List<History> mHistory = new ArrayList<>();


    public static HistoryRepository getInstance() {
        return sHistoryRepository;
    }

    private HistoryRepository() {

    }

    public List<History> getHistory() {
        return mHistory;
    }

    public void writeHistory(History historyInstance) {
        mHistory.add(historyInstance);
    }
}
