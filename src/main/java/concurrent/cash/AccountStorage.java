package concurrent.cash;

import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            return accounts.putIfAbsent(account.id(), account) == null;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            return accounts.replace(account.id(), accounts.get(account.id()), account);
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            return accounts.remove(id) != null;
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            Account account = accounts.get(id);
            if (account != null) {
                account = new Account(account.id(), account.amount());
            }
            return Optional.ofNullable(account);
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;

        synchronized (accounts) {
            Account accFrom = accounts.get(fromId);
            Account accTo = accounts.get(toId);


            if (accFrom == null || accTo == null) {
                throw new IllegalArgumentException("Неверный номер счета отправления/получения");
            }

            if (accFrom.amount() < amount) {
                throw new IllegalArgumentException("Недостачно средств на счете-отправителе");
            }

            update(new Account(accFrom.id(), accFrom.amount() - amount));
            update(new Account(accTo.id(), accTo.amount() + amount));
        }
        return rsl;
    }
}
