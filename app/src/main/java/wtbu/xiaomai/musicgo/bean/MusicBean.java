package wtbu.xiaomai.musicgo.bean;

public class MusicBean {
    private String songName;
    private String singerName;
    private String albumName;
    private String songId;

    public MusicBean() {
    }

    public MusicBean(String songName, String singerName, String albumName, String songId) {
        this.songName = songName;
        this.singerName = singerName;
        this.albumName = albumName;
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "songName='" + songName + '\'' +
                ", singerName='" + singerName + '\'' +
                ", albumName='" + albumName + '\'' +
                ", songId=" + songId +
                '}';
    }
}
