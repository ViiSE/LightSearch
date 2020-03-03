package test.data.processor;

import lightsearch.admin.panel.data.ScannerChooserCommandDTO;
import lightsearch.admin.panel.data.creator.ScannerChooserCommandDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerChooserCommandDTOCreatorInit;
import lightsearch.admin.panel.scanner.ScannerChooserCommandInit;

public class ScannerChooserCommandDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... ignore) {
        ScannerChooserCommandDTOCreator scChCmdCreator =
                ScannerChooserCommandDTOCreatorInit.scannerChooserCommandDTOCreator();
        ScannerChooserCommandDTO scChCmdDTO = scChCmdCreator.createScannerChooserCommandDTO();

        return ScannerChooserCommandInit.scannerChooserCommand(scChCmdDTO);
    }
}
