/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Osnova {

    public static final Osnova instance = new Osnova();

    private final Map<String, AccGroup> accGroupsByNumber = new TreeMap<>();

    private Osnova() {
        InputStream gris = AccGroup.class.getResourceAsStream("/acc/model/osnova.csv");
        Stream<String> lines = new BufferedReader(new InputStreamReader(gris, StandardCharsets.UTF_8)).lines();
        lines.forEach((line) -> {
            String[] fields = line.split(";");
            String number = fields[0].trim();
            if (number.equals("REMOVE")) {
                return;
            }
            String name = fields[1].trim();
            GroupEnum type;
            switch (number.length()) {
                case 1: {
                    type = GroupEnum.CLASS;
                    break;
                }
                case 2: {
                    type = GroupEnum.GROUP;
                    break;
                }
                case 3: {
                    type = GroupEnum.SYNT_ACCOUNT;
                    break;
                }
                default:
                    throw new RuntimeException();
            }
            AccGroup ac = new AccGroup(type, number, name);
            accGroupsByNumber.put(ac.getNumber(), ac);
        });
        accGroupsByNumber.values().stream()
                .filter((ag) -> ag.getGroupType() != GroupEnum.CLASS)
                .forEach((ag) -> {
                    String parNumber = ag.getNumber().substring(0, ag.getNumber().length() - 1);
                    ag.setParent(accGroupsByNumber.get(parNumber));
                });

    }

    public AccGroup getTridaZuctovaciVztahy() {
        return accGroupsByNumber.get("3");
    }

    public AccGroup getTridaNaklady() {
        return accGroupsByNumber.get("5");
    }

    public AccGroup getDodavetele() {
        return accGroupsByNumber.get("321");
    }

    public AccGroup getMaterial() {
        return accGroupsByNumber.get("321");
    }

    public AccGroup getPocatecniUcetRozvazny() {
        return accGroupsByNumber.get("961");
    }

    public Optional<AccGroup> getGroup(String number) {
        return Optional.ofNullable(accGroupsByNumber.get(number));
    }

    public List<AccGroup> getGroups() {
        return accGroupsByNumber.values()
                .stream()
                .collect(Collectors.toList());
    }

    public List<AccGroup> syntAccounts() {
        return accGroupsByNumber.values().stream()
                .filter((a) -> a.getGroupType() == GroupEnum.SYNT_ACCOUNT)
                .collect(Collectors.toList());
    }

    public List<AccGroup> getSubGroups(String prefix) {
        return getGroups().stream()
                .filter((g) -> g.getNumber().startsWith(prefix))
                .collect(Collectors.toList());
    }
}
