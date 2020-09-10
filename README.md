# github-updater
Atualize automaticamente seus plugins de acordo com a última versão em release disponível no projeto do github público ou privado.

## Plataformas disponíveis
 - Bukkit
 - Bungeecord (em desenvolvimento)
 
## Modo de uso

### Bukkit:
`````java
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        if (pluginManager.isPluginEnabled("github-updater")) {
            Updater updater = BukkitUpdater.getUpdater();
            updater.registerPlugin(this.getName(), "repository-name");   
        }
    }

}
`````