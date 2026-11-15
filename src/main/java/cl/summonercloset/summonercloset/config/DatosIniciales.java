package cl.summonercloset.summonercloset.config;

import cl.summonercloset.summonercloset.campeon.Campeon;
import cl.summonercloset.summonercloset.campeon.CampeonRepository;
import cl.summonercloset.summonercloset.chroma.Chroma;
import cl.summonercloset.summonercloset.chroma.ChromaRepository;
import cl.summonercloset.summonercloset.posicion.Posicion;
import cl.summonercloset.summonercloset.posicion.PosicionRepository;
import cl.summonercloset.summonercloset.skin.Skin;
import cl.summonercloset.summonercloset.skin.SkinRepository;
import cl.summonercloset.summonercloset.usuario.RolUsuario;
import cl.summonercloset.summonercloset.usuario.RolUsuarioRepository;
import cl.summonercloset.summonercloset.usuario.Usuario;
import cl.summonercloset.summonercloset.usuario.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DatosIniciales implements CommandLineRunner {

    private final PosicionRepository posicionRepository;
    private final CampeonRepository campeonRepository;
    private final SkinRepository skinRepository;
    private final ChromaRepository chromaRepository;
    private final RolUsuarioRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public DatosIniciales(PosicionRepository posicionRepository, CampeonRepository campeonRepository, SkinRepository skinRepository, ChromaRepository chromaRepository, RolUsuarioRepository rolRepository, UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.posicionRepository = posicionRepository;
        this.campeonRepository = campeonRepository;
        this.skinRepository = skinRepository;
        this.chromaRepository = chromaRepository;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        cargarPosiciones();
        cargarCampeones();
        cargarSkins();
        cargarChromas();
        cargarRoles();
        cargarUsuarios();
    }

    private void cargarRoles() {
        if (rolRepository.count() > 0) {
            return;
        }
        rolRepository.saveAll(List.of(
                new RolUsuario("admin", "Administrador"),
                new RolUsuario("cliente", "Cliente")));
    }

    private void cargarUsuarios() {
        if (usuarioRepository.count() > 0) {
            return;
        }
        RolUsuario admin = rolRepository.findById("admin").orElseThrow();
        RolUsuario cliente = rolRepository.findById("cliente").orElseThrow();
        usuarioRepository.saveAll(List.of(
                new Usuario("administrador", "admin@summonercloset.cl", encoder.encode("admin123"), admin),
                new Usuario("invocador", "usuario@correo.com", encoder.encode("user12345"), cliente)));
    }

    private void cargarPosiciones() {
        if (posicionRepository.count() > 0) {
            return;
        }
        posicionRepository.saveAll(List.of(
                new Posicion("top", "Top", "imagenes/global/Top_icon.webp"),
                new Posicion("jungle", "Jungle", "imagenes/global/Jungle_icon.webp"),
                new Posicion("middle", "Middle", "imagenes/global/Middle_icon.webp"),
                new Posicion("bottom", "Bottom", "imagenes/global/Bottom_icon.webp"),
                new Posicion("support", "Support", "imagenes/global/Support_icon.webp")));
    }

    private void cargarCampeones() {
        if (campeonRepository.count() > 0) {
            return;
        }
        Posicion top = posicionRepository.findById("top").orElseThrow();
        Posicion jungle = posicionRepository.findById("jungle").orElseThrow();
        Posicion middle = posicionRepository.findById("middle").orElseThrow();
        Posicion bottom = posicionRepository.findById("bottom").orElseThrow();
        Posicion support = posicionRepository.findById("support").orElseThrow();

        List<Campeon> campeones = new ArrayList<>();
        campeones.add(campeon("aatrox", "Aatrox", top, "Aatrox/Aatrox.webp"));
        campeones.add(campeon("ahri", "Ahri", middle, "Ahri/Ahri.webp"));
        campeones.add(campeon("akali", "Akali", middle, "Akali/Akali.webp"));
        campeones.add(campeon("akshan", "Akshan", middle, "Akshan/Akshan.webp"));
        campeones.add(campeon("alistar", "Alistar", support, "Alistar/Alistar.webp"));
        campeones.add(campeon("ambessa", "Ambessa", top, "Ambessa/Ambessa.webp"));
        campeones.add(campeon("amumu", "Amumu", jungle, "Amumu/Amumu.webp"));
        campeones.add(campeon("anivia", "Anivia", middle, "Anivia/Anivia.webp"));
        campeones.add(campeon("annie", "Annie", middle, "Annie/Annie.webp"));
        campeones.add(campeon("aphelios", "Aphelios", bottom, "Aphelios/Aphelios.webp"));
        campeones.add(campeon("ashe", "Ashe", bottom, "Ashe/Ashe.webp"));
        campeones.add(campeon("aurelion_sol", "Aurelion Sol", middle, "Aurelion_Sol/Aurelion_Sol.webp"));
        campeones.add(campeon("aurora", "Aurora", middle, "Aurora/Aurora.webp"));
        campeones.add(campeon("azir", "Azir", middle, "Azir/Azir.webp"));
        campeones.add(campeon("bard", "Bard", support, "Bard/Bard.webp"));
        campeones.add(campeon("belveth", "BelVeth", jungle, "BelVeth/Belveth.webp"));
        campeones.add(campeon("blitzcrank", "Blitzcrank", support, "Blitzcrank/Blitzcrank.webp"));
        campeones.add(campeon("brand", "Brand", support, "Brand/Brand.webp"));
        campeones.add(campeon("braum", "Braum", support, "Braum/Braum.webp"));
        campeones.add(campeon("briar", "Briar", jungle, "Briar/Briar.webp"));
        campeones.add(campeon("caitlyn", "Caitlyn", bottom, "Caitlyn/Caitlyn.webp"));
        campeones.add(campeon("camille", "Camille", top, "Camille/Camille.webp"));
        campeones.add(campeon("cassiopeia", "Cassiopeia", middle, "Cassiopeia/Cassiopeia.webp"));
        campeones.add(campeon("chogath", "ChoGath", top, "ChoGath/ChoGath.webp"));
        campeones.add(campeon("corki", "Corki", bottom, "Corki/Corki.webp"));
        campeones.add(campeon("darius", "Darius", top, "Darius/Darius.webp"));
        campeones.add(campeon("diana", "Diana", jungle, "Diana/Diana.webp"));
        campeones.add(campeon("draven", "Draven", bottom, "Draven/Draven.webp"));
        campeones.add(campeon("drmundo", "DrMundo", top, "DrMundo/DrMundo.webp"));
        campeones.add(campeon("ekko", "Ekko", jungle, "Ekko/Ekko.webp"));
        campeones.add(campeon("elise", "Elise", jungle, "Elise/Elise.webp"));
        campeones.add(campeon("evelynn", "Evelynn", jungle, "Evelynn/Evelynn.webp"));
        campeones.add(campeon("ezreal", "Ezreal", bottom, "Ezreal/Ezreal.webp"));
        campeones.add(campeon("fiddlesticks", "Fiddlesticks", jungle, "Fiddlesticks/Fiddlesticks.webp"));
        campeones.add(campeon("fiora", "Fiora", top, "Fiora/Fiora.webp"));
        campeones.add(campeon("fizz", "Fizz", middle, "Fizz/Fizz.webp"));
        campeones.add(campeon("galio", "Galio", middle, "Galio/Galio.webp"));
        campeones.add(campeon("gangplank", "Gangplank", top, "Gangplank/Gangplank.webp"));
        campeones.add(campeon("garen", "Garen", top, "Garen/Garen.webp"));
        campeones.add(campeon("gnar", "Gnar", top, "Gnar/Gnar.webp"));
        campeones.add(campeon("gragas", "Gragas", jungle, "Gragas/Gragas.webp"));
        campeones.add(campeon("graves", "Graves", jungle, "Graves/Graves.webp"));
        campeones.add(campeon("gwen", "Gwen", top, "Gwen/Gwen.webp"));
        campeones.add(campeon("hecarim", "Hecarim", jungle, "Hecarim/Hecarim.webp"));
        campeones.add(campeon("heimerdinger", "Heimerdinger", top, "Heimerdinger/Heimerdinger.webp"));
        campeones.add(campeon("hwei", "Hwei", middle, "Hwei/Hwei.webp"));
        campeones.add(campeon("illaoi", "Illaoi", top, "Illaoi/Illaoi.webp"));
        campeones.add(campeon("irelia", "Irelia", top, "Irelia/Irelia.webp"));
        campeones.add(campeon("ivern", "Ivern", jungle, "Ivern/Ivern.webp"));
        campeones.add(campeon("janna", "Janna", support, "Janna/Janna.webp"));
        campeones.add(campeon("jarvaniv", "JarvanIV", jungle, "JarvanIV/JarvanIV.webp"));
        campeones.add(campeon("jax", "Jax", top, "Jax/Jax.webp"));
        campeones.add(campeon("jayce", "Jayce", top, "Jayce/Jayce.webp"));
        campeones.add(campeon("jhin", "Jhin", bottom, "Jhin/Jhin.webp"));
        campeones.add(campeon("jinx", "Jinx", bottom, "Jinx/Jinx.webp"));
        campeones.add(campeon("kaisa", "KaiSa", bottom, "KaiSa/KaiSa.webp"));
        campeones.add(campeon("kalista", "Kalista", bottom, "Kalista/Kalista.webp"));
        campeones.add(campeon("karma", "Karma", support, "Karma/Karma.webp"));
        campeones.add(campeon("karthus", "Karthus", jungle, "Karthus/Karthus.webp"));
        campeones.add(campeon("kassadin", "Kassadin", middle, "Kassadin/Kassadin.webp"));
        campeones.add(campeon("katarina", "Katarina", middle, "Katarina/Katarina.webp"));
        campeones.add(campeon("kayle", "Kayle", top, "Kayle/Kayle.webp"));
        campeones.add(campeon("kayn", "Kayn", jungle, "Kayn/Kayn.webp"));
        campeones.add(campeon("kennen", "Kennen", top, "Kennen/Kennen.webp"));
        campeones.add(campeon("khazix", "KhaZix", jungle, "KhaZix/KhaZix.webp"));
        campeones.add(campeon("kindred", "Kindred", jungle, "Kindred/Kindred.webp"));
        campeones.add(campeon("kled", "Kled", top, "Kled/Kled.webp"));
        campeones.add(campeon("kogmaw", "KogMaw", bottom, "KogMaw/KogMaw.webp"));
        campeones.add(campeon("ksante", "KSante", top, "KSante/KSante.webp"));
        campeones.add(campeon("leblanc", "LeBlanc", middle, "LeBlanc/LeBlanc.webp"));
        campeones.add(campeon("leesin", "LeeSin", jungle, "LeeSin/LeeSin.webp"));
        campeones.add(campeon("leona", "Leona", support, "Leona/Leona.webp"));
        campeones.add(campeon("lillia", "Lillia", jungle, "Lillia/Lillia.webp"));
        campeones.add(campeon("lissandra", "Lissandra", middle, "Lissandra/Lissandra.webp"));
        campeones.add(campeon("locke", "Locke", middle, "Locke/Locke.webp"));
        campeones.add(campeon("lucian", "Lucian", bottom, "Lucian/Lucian.webp"));
        campeones.add(campeon("lulu", "Lulu", support, "Lulu/Lulu.webp"));
        campeones.add(campeon("lux", "Lux", middle, "Lux/Lux.webp"));
        campeones.add(campeon("malphite", "Malphite", top, "Malphite/Malphite.webp"));
        campeones.add(campeon("malzahar", "Malzahar", middle, "Malzahar/Malzahar.webp"));
        campeones.add(campeon("maokai", "Maokai", support, "Maokai/Maokai.webp"));
        campeones.add(campeon("masteryi", "MasterYi", jungle, "MasterYi/MasterYi.webp"));
        campeones.add(campeon("mel", "Mel", middle, "Mel/Mel.webp"));
        campeones.add(campeon("milio", "Milio", support, "Milio/Milio.webp"));
        campeones.add(campeon("missfortune", "MissFortune", bottom, "MissFortune/MissFortune.webp"));
        campeones.add(campeon("mordekaiser", "Mordekaiser", top, "Mordekaiser/Mordekaiser.webp"));
        campeones.add(campeon("morgana", "Morgana", support, "Morgana/Morgana.webp"));
        campeones.add(campeon("naafiri", "Naafiri", middle, "Naafiri/Naafiri.webp"));
        campeones.add(campeon("nami", "Nami", support, "Nami/Nami.webp"));
        campeones.add(campeon("nasus", "Nasus", top, "Nasus/Nasus.webp"));
        campeones.add(campeon("nautilus", "Nautilus", support, "Nautilus/Nautilus.webp"));
        campeones.add(campeon("neeko", "Neeko", middle, "Neeko/Neeko.webp"));
        campeones.add(campeon("nidalee", "Nidalee", jungle, "Nidalee/Nidalee.webp"));
        campeones.add(campeon("nilah", "Nilah", bottom, "Nilah/Nilah.webp"));
        campeones.add(campeon("nocturne", "Nocturne", jungle, "Nocturne/Nocturne.webp"));
        campeones.add(campeon("nunu", "Nunu", jungle, "Nunu/Nunu.webp"));
        campeones.add(campeon("olaf", "Olaf", top, "Olaf/Olaf.webp"));
        campeones.add(campeon("orianna", "Orianna", middle, "Orianna/Orianna.webp"));
        campeones.add(campeon("ornn", "Ornn", top, "Ornn/Ornn.webp"));
        campeones.add(campeon("pantheon", "Pantheon", top, "Pantheon/Pantheon.webp"));
        campeones.add(campeon("poppy", "Poppy", jungle, "Poppy/Poppy.webp"));
        campeones.add(campeon("pyke", "Pyke", support, "Pyke/Pyke.webp"));
        campeones.add(campeon("qiyana", "Qiyana", middle, "Qiyana/Qiyana.webp"));
        campeones.add(campeon("quinn", "Quinn", top, "Quinn/Quinn.webp"));
        campeones.add(campeon("rakan", "Rakan", support, "Rakan/Rakan.webp"));
        campeones.add(campeon("rammus", "Rammus", jungle, "Rammus/Rammus.webp"));
        campeones.add(campeon("reksai", "RekSai", jungle, "RekSai/RekSai.webp"));
        campeones.add(campeon("rell", "Rell", support, "Rell/Rell.webp"));
        campeones.add(campeon("renataglasc", "RenataGlasc", support, "RenataGlasc/RenataGlasc.webp"));
        campeones.add(campeon("renekton", "Renekton", top, "Renekton/Renekton.webp"));
        campeones.add(campeon("rengar", "Rengar", jungle, "Rengar/Rengar.webp"));
        campeones.add(campeon("riven", "Riven", top, "Riven/Riven.webp"));
        campeones.add(campeon("rumble", "Rumble", top, "Rumble/Rumble.webp"));
        campeones.add(campeon("ryze", "Ryze", middle, "Ryze/Ryze.webp"));
        campeones.add(campeon("samira", "Samira", bottom, "Samira/Samira.webp"));
        campeones.add(campeon("sejuani", "Sejuani", jungle, "Sejuani/Sejuani.webp"));
        campeones.add(campeon("senna", "Senna", support, "Senna/Senna.webp"));
        campeones.add(campeon("seraphine", "Seraphine", support, "Seraphine/Seraphine.webp"));
        campeones.add(campeon("sett", "Sett", top, "Sett/Sett.webp"));
        campeones.add(campeon("shaco", "Shaco", jungle, "Shaco/Shaco.webp"));
        campeones.add(campeon("shen", "Shen", top, "Shen/Shen.webp"));
        campeones.add(campeon("shyvana", "Shyvana", jungle, "Shyvana/Shyvana.webp"));
        campeones.add(campeon("singed", "Singed", top, "Singed/Singed.webp"));
        campeones.add(campeon("sion", "Sion", top, "Sion/Sion.webp"));
        campeones.add(campeon("sivir", "Sivir", bottom, "Sivir/Sivir.webp"));
        campeones.add(campeon("skarner", "Skarner", jungle, "Skarner/Skarner.webp"));
        campeones.add(campeon("smolder", "Smolder", bottom, "Smolder/Smolder.webp"));
        campeones.add(campeon("sona", "Sona", support, "Sona/Sona.webp"));
        campeones.add(campeon("soraka", "Soraka", support, "Soraka/Soraka.webp"));
        campeones.add(campeon("swain", "Swain", support, "Swain/Swain.webp"));
        campeones.add(campeon("sylas", "Sylas", middle, "Sylas/Sylas.webp"));
        campeones.add(campeon("syndra", "Syndra", middle, "Syndra/Syndra.webp"));
        campeones.add(campeon("tahmkench", "TahmKench", top, "TahmKench/TahmKench.webp"));
        campeones.add(campeon("taliyah", "Taliyah", jungle, "Taliyah/Taliyah.webp"));
        campeones.add(campeon("talon", "Talon", middle, "Talon/Talon.webp"));
        campeones.add(campeon("taric", "Taric", support, "Taric/Taric.webp"));
        campeones.add(campeon("teemo", "Teemo", top, "Teemo/Teemo.webp"));
        campeones.add(campeon("thresh", "Thresh", support, "Thresh/Thresh.webp"));
        campeones.add(campeon("tristana", "Tristana", bottom, "Tristana/Tristana.webp"));
        campeones.add(campeon("trundle", "Trundle", top, "Trundle/Trundle.webp"));
        campeones.add(campeon("tryndamere", "Tryndamere", top, "Tryndamere/Tryndamere.webp"));
        campeones.add(campeon("twistedfate", "TwistedFate", middle, "TwistedFate/TwistedFate.webp"));
        campeones.add(campeon("twitch", "Twitch", bottom, "Twitch/Twitch.webp"));
        campeones.add(campeon("udyr", "Udyr", jungle, "Udyr/Udyr.webp"));
        campeones.add(campeon("urgot", "Urgot", top, "Urgot/Urgot.webp"));
        campeones.add(campeon("varus", "Varus", bottom, "Varus/Varus.webp"));
        campeones.add(campeon("vayne", "Vayne", bottom, "Vayne/Vayne.webp"));
        campeones.add(campeon("veigar", "Veigar", middle, "Veigar/Veigar.webp"));
        campeones.add(campeon("velkoz", "VelKoz", support, "VelKoz/VelKoz.webp"));
        campeones.add(campeon("vex", "Vex", middle, "Vex/Vex.webp"));
        campeones.add(campeon("vi", "Vi", jungle, "Vi/Vi.webp"));
        campeones.add(campeon("viego", "Viego", jungle, "Viego/Viego.webp"));
        campeones.add(campeon("viktor", "Viktor", middle, "Viktor/Viktor.webp"));
        campeones.add(campeon("vladimir", "Vladimir", middle, "Vladimir/Vladimir.webp"));
        campeones.add(campeon("volibear", "Volibear", jungle, "Volibear/Volibear.webp"));
        campeones.add(campeon("warwick", "Warwick", jungle, "Warwick/Warwick.webp"));
        campeones.add(campeon("wukong", "Wukong", jungle, "Wukong/Wukong.webp"));
        campeones.add(campeon("xayah", "Xayah", bottom, "Xayah/Xayah.webp"));
        campeones.add(campeon("xerath", "Xerath", middle, "Xerath/Xerath.webp"));
        campeones.add(campeon("xinzhao", "XinZhao", jungle, "XinZhao/XinZhao.webp"));
        campeones.add(campeon("yasuo", "Yasuo", middle, "Yasuo/Yasuo.webp"));
        campeones.add(campeon("yone", "Yone", middle, "Yone/Yone.webp"));
        campeones.add(campeon("yorick", "Yorick", top, "Yorick/Yorick.webp"));
        campeones.add(campeon("yunara", "Yunara", bottom, "Yunara/Yunara.webp"));
        campeones.add(campeon("yuumi", "Yuumi", support, "Yuumi/Yuumi.webp"));
        campeones.add(campeon("zaahen", "Zaahen", top, "Zaahen/Zaahen.webp"));
        campeones.add(campeon("zac", "Zac", jungle, "Zac/Zac.webp"));
        campeones.add(campeon("zed", "Zed", middle, "Zed/Zed.webp"));
        campeones.add(campeon("zeri", "Zeri", bottom, "Zeri/Zeri.webp"));
        campeones.add(campeon("ziggs", "Ziggs", middle, "Ziggs/Ziggs.webp"));
        campeones.add(campeon("zilean", "Zilean", support, "Zilean/Zilean.webp"));
        campeones.add(campeon("zoe", "Zoe", middle, "Zoe/Zoe.webp"));
        campeones.add(campeon("zyra", "Zyra", support, "Zyra/Zyra.webp"));

        campeonRepository.saveAll(campeones);
    }

    private void cargarSkins() {
        if (skinRepository.count() > 0) {
            return;
        }
        List<Skin> skins = new ArrayList<>();

        skinRepository.saveAll(skins);
    }

    private void cargarChromas() {
        if (chromaRepository.count() > 0) {
            return;
        }
        List<Chroma> chromas = new ArrayList<>();


        chromaRepository.saveAll(chromas);
    }

    private Campeon campeon(String id, String name, Posicion role, String imagen) {
        return new Campeon(id, name, role, "imagenes/campeones/" + imagen,
                name + " es un campeón legendario de Runaterra dentro del universo de League of Legends.");
    }
}