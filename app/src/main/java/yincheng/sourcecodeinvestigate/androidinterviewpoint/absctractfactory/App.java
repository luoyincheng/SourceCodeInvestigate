package yincheng.sourcecodeinvestigate.androidinterviewpoint.absctractfactory;

/**
 * Created by yincheng on 2018/5/15/18:53.
 * github:luoyincheng
 */
public class App {

    private King king;
    private Castle castle;
    private Army army;

    public King getKing() {
        return king;
    }

    public void setKing(King king) {
        this.king = king;
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public Army getArmy() {
        return army;
    }

    public void setArmy(Army army) {
        this.army = army;
    }

    public void createKingdom(final KingdomFactory factory) {
        setKing(factory.createKing());
        setArmy(factory.createArmy());
        setCastle(factory.createCastle());
    }



    public static class FactoryMaker {
        public enum kingdomType {
            ORC, ELF
        }

        public static KingdomFactory makeFactory(kingdomType type) {
            switch (type) {
                case ORC:
                    return new OrcKingdomFactory();
                case ELF:
                    return new ElfKingdomFactory();
                default:
                    throw new IllegalArgumentException("暂时不支持该类型");
            }
        }
    }
}
