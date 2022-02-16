package com.hce.ducati;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Genres;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v23Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class MP3Convertion {

	public static void main(String[] args) throws IOException, UnsupportedTagException, InvalidDataException, NotSupportedException {
//		charsetsInfo(SRC_DIR_LOCATION+"/邓丽君/夜色.mp3", "ISO-8859-1", "GBK");
		/*File[] files = new File(SRC_DIR_LOCATION).listFiles();
		for(File file:files) {
			if(file.isDirectory())
				System.out.println("String dirName = \""+file.getName()+"\";");
		}*/
//		String dirName = "孙佳星";
//		String dirName = "许美静";
//		String dirName = "张学友";
//		String dirName = "Beyond";
//		String dirName = "Richard Clayderman";
//		String dirName = "最新流行";
//		String dirName = "Bandari";
//		String dirName = "将爱情进行到底";
//		String dirName = "八音盒星光音乐小品";
//		String dirName = "Celtic TreasureⅡ";
//		String dirName = "张信哲";
//		String dirName = "Kenny G";
		String dirName = "经典老掉牙";
//		String dirName = "平安";
//		String dirName = "礼仪曲";
//		String dirName = "同一首歌";
//		String dirName = "港台影视";
//		String dirName = "老情歌";
//		String dirName = "刘德华";
//		String dirName = "休闲音乐";
//		String dirName = "Yanni";
//		String dirName = "许茹芸";
//		String dirName = "任贤齐";
//		String dirName = "成龙";
//		String dirName = "孟庭苇";
//		String dirName = "陈慧娴";
//		String dirName = "杨钰莹";
//		String dirName = "邓丽君";
//		String dirName = "陈明";
//		String dirName = "欧美怀旧金曲";
//		String dirName = "PolyGram";
//		String dirName = "情歌对唱";
//		String dirName = "校园民谣";
//		String dirName = "世界名曲";
//		String dirName = "王菲";
//		String dirName = "内地影视";
//		String dirName = "Music Box Dancer";
//		String dirName = "张雨生";
//		String dirName = "陈百强";
//		String dirName = "周华健";
//		String dirName = "高胜美";

		tree(new File(SRC_DIR_LOCATION+"/"+dirName), null);
		System.out.println("Total: "+count);
//		String location = DST_DIR_LOCATION+"/孙佳星/咪姆.mp3";
//		convertAttributes(new Mp3File(location), location, null);

//		ID3v23Tag id3v2Tag = new ID3v23Tag();
//		id3v2Tag.setArtist("群星");
//		setAttributes(id3v2Tag, DST_DIR_LOCATION+"/经典老掉牙/世界需要热心肠.mp3");
	}

	private final static String SRC_DIR_LOCATION = "/Users/maqiang/Quincy/Media/MP3_ISO-8859-1";
	private final static String DST_DIR_LOCATION = "/Users/maqiang/Quincy/Media/MP3_UTF-8";
	private static int count = 0;

	private static void tree(File parent, String dstGenreDescription) throws UnsupportedTagException, InvalidDataException, NotSupportedException, IOException {
		if(parent.isFile()) {
			String name = parent.getName();
			if(name.endsWith(".mp3")||name.endsWith(".MP3")) {
				count++;
				process(parent, dstGenreDescription);
			}
		} else if(parent.isDirectory()) {
			File utf8Dir = new File(parent.getAbsolutePath().replaceAll(SRC_DIR_LOCATION, DST_DIR_LOCATION));
			System.out.println("DIR============="+utf8Dir.getAbsolutePath());
			if(!utf8Dir.exists())
				utf8Dir.mkdirs();
			File[] files = parent.listFiles();
			for(File file:files)
				tree(file, dstGenreDescription);
		}
	}

	private static void convertAttributes(Mp3File mp3file, String location, String dstGenreDescription) throws IOException, UnsupportedTagException, InvalidDataException, NotSupportedException {
		ID3v2 id3v2Tag = mp3file.getId3v2Tag();
		if(id3v2Tag!=null) {
			String title = trim(id3v2Tag.getTitle());
			String artist = trim(id3v2Tag.getArtist());
			String album = trim(id3v2Tag.getAlbum());
			String comment = trim(id3v2Tag.getComment());
			String genreDescription = trim(id3v2Tag.getGenreDescription());
			String albumArtist = trim(id3v2Tag.getAlbumArtist());
			String composer = trim(id3v2Tag.getComposer());
			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
			if(id3v1Tag==null) {
				id3v1Tag = new ID3v1Tag();
				mp3file.setId3v1Tag(id3v1Tag);
			}
			if(title!=null) {
				title = convert(id3v2Tag.getTitle());
				id3v2Tag.setTitle(title);
				id3v1Tag.setTitle(title);
			}
			if(artist!=null) {
				artist = convert(artist);
				id3v2Tag.setArtist(artist);
				id3v1Tag.setArtist(artist);
			}
			if(album!=null) {
				album = convert(album);
				id3v2Tag.setAlbum(album);
				id3v1Tag.setAlbum(album);
			}
			if(comment!=null) {
				comment = convert(comment);
				id3v2Tag.setComment(comment);
				id3v1Tag.setComment(comment);
			}
			if(albumArtist!=null) {
				albumArtist = convert(albumArtist);
				id3v2Tag.setAlbumArtist(albumArtist);
			}
			if(composer!=null) {
				composer = convert(composer);
				id3v2Tag.setComposer(composer);
			}
			if(dstGenreDescription!=null) {
				genreDescription = dstGenreDescription;
			} else {
				if(genreDescription==null) {
					genreDescription = "Other";
				} else {
					int genre = ID3v1Genres.matchGenreDescription(genreDescription);
					if(genre<0)
						genreDescription = genreDescriptionMap.get(genreDescription);
				}
			}
			id3v2Tag.setGenreDescription(genreDescription);
			id3v1Tag.setGenre(ID3v1Genres.matchGenreDescription(genreDescription));
//			System.out.println("Title: "+id3v2Tag.getTitle()+"---"+title);
//			System.out.println("Artist: "+id3v2Tag.getArtist()+"---"+artist);
//			System.out.println("Album: "+id3v2Tag.getAlbum()+"---"+album);
//			System.out.println("Comment: "+id3v2Tag.getComment()+"---"+comment);
//			System.out.println("Album Artist: "+id3v2Tag.getAlbumArtist()+"---"+albumArtist);
//			System.out.println("Composer: "+id3v2Tag.getComposer()+"---"+composer);
			System.out.println("Genre: "+id3v2Tag.getGenre());
			System.out.println("Genre Description: "+id3v2Tag.getGenreDescription());

//			System.out.println("AlbumImageMimeType: "+id3v2Tag.getAlbumImageMimeType());
//			System.out.println("ArtistUrl: "+id3v2Tag.getArtistUrl());
//			System.out.println("AudiofileUrl: "+id3v2Tag.getAudiofileUrl());
//			System.out.println("BPM: "+id3v2Tag.getBPM());
//			System.out.println("CommercialUrl: "+id3v2Tag.getCommercialUrl());
//			System.out.println("Copyright: "+id3v2Tag.getCopyright());
//			System.out.println("CopyrightUrl: "+id3v2Tag.getCopyrightUrl());
//			System.out.println("DataLength: "+id3v2Tag.getDataLength());
//			System.out.println("Date: "+id3v2Tag.getDate());
//			System.out.println("Encoder: "+id3v2Tag.getEncoder());
//			System.out.println("ItunesComment: "+id3v2Tag.getItunesComment());
//			System.out.println("Key: "+id3v2Tag.getKey());
//			System.out.println("Length: "+id3v2Tag.getLength());
//			System.out.println("Lyrics: "+id3v2Tag.getLyrics());
//			System.out.println("OriginalArtist: "+id3v2Tag.getOriginalArtist());
//			System.out.println("PartOfSet: "+id3v2Tag.getPartOfSet());
//			System.out.println("PaymentUrl: "+id3v2Tag.getPaymentUrl());
//			System.out.println("Publisher: "+id3v2Tag.getPublisher());
//			System.out.println("PublisherUrl: "+id3v2Tag.getPublisherUrl());
//			System.out.println("Padding: "+id3v2Tag.getPadding());
//			System.out.println("RadiostationUrl: "+id3v2Tag.getRadiostationUrl());
//			System.out.println("Track: "+id3v2Tag.getTrack());
//			System.out.println("Url: "+id3v2Tag.getUrl());
//			System.out.println("Version: "+id3v2Tag.getVersion());
//			System.out.println("WmpRating: "+id3v2Tag.getWmpRating());
//			System.out.println("Year: "+id3v2Tag.getYear());
//			System.out.println("ObseleteFormat: "+id3v2Tag.getObseleteFormat());
//			System.out.println("Track: "+id3v1Tag.getTrack());
//			System.out.println("Version: "+id3v1Tag.getVersion());
//			System.out.println("Year: "+id3v1Tag.getYear());
		} else
			System.err.println("NO_ID3V2TAG----------"+location);
	}

	private static void process(File file, String dstGenreDescription) throws UnsupportedTagException, InvalidDataException, IOException, NotSupportedException {
		String newLocation = file.getAbsolutePath().replaceAll(SRC_DIR_LOCATION, DST_DIR_LOCATION);
		System.out.println("=================================");
		System.out.println("New Location: "+newLocation);
		Mp3File mp3file = new Mp3File(file);
		convertAttributes(mp3file, file.getAbsolutePath(), dstGenreDescription);
		mp3file.save(newLocation);
	}

	private static String convert(String s) throws UnsupportedEncodingException {
		return new String(s.getBytes("ISO-8859-1"), "GBK");
	}

	public static void setAttributes(ID3v2 id3v2TagSrc, String location) throws UnsupportedTagException, InvalidDataException, IOException, NotSupportedException {
		Mp3File mp3file = new Mp3File(new File(location));
		ID3v2 id3v2Tag = mp3file.getId3v2Tag();
		if(id3v2Tag==null) {
			id3v2Tag = new ID3v23Tag();
			mp3file.setId3v2Tag(id3v2Tag);
		}
		ID3v1 id3v1Tag = mp3file.getId3v1Tag();
		if(id3v1Tag==null) {
			id3v1Tag = new ID3v1Tag();
			mp3file.setId3v1Tag(id3v1Tag);
		}
		String title = trim(id3v2TagSrc.getTitle());
		String artist = trim(id3v2TagSrc.getArtist());
		String album = trim(id3v2TagSrc.getAlbum());
		String comment = trim(id3v2TagSrc.getComment());
		if(title!=null) {
			id3v2Tag.setTitle(title);
			id3v1Tag.setTitle(title);
		}
		if(artist!=null) {
			id3v2Tag.setArtist(artist);
			id3v1Tag.setArtist(artist);
		}
		if(album!=null) {
			id3v2Tag.setAlbum(album);
			id3v1Tag.setAlbum(album);
		}
		if(comment!=null) {
			id3v2Tag.setComment(comment);
			id3v1Tag.setComment(comment);
		}
		String genreDescription = trim(id3v2TagSrc.getGenreDescription());
		if(genreDescription!=null) {
			id3v2Tag.setGenreDescription(genreDescription);
			id3v1Tag.setGenre(ID3v1Genres.matchGenreDescription(genreDescription));
		}
		String newLocation = new StringBuilder(location).insert(location.length()-4, "_xxx").toString();
		mp3file.save(newLocation);
	}

	public static String trim(String s) {
		if(s!=null) {
			String _s = s.trim();
			if(_s.length()>0)
				return _s;
		}
		return null;
	}

	private final static Map<String, String> genreDescriptionMap = new HashMap<String, String>();
	static {
		genreDescriptionMap.put("流行", "Pop");
		genreDescriptionMap.put("港台", "Pop");
		genreDescriptionMap.put("未知", "Other");
		genreDescriptionMap.put("genre", "Other");
		genreDescriptionMap.put("A capella A Cappella", "Other");
		genreDescriptionMap.put("General Pop", "Pop");
		genreDescriptionMap.put("Holiday", "Other");
		genreDescriptionMap.put("Christmas", "Classical");
		genreDescriptionMap.put("未知名", "Other");
		genreDescriptionMap.put("军旅", "Other");
		genreDescriptionMap.put("中国军魂军歌试听中心", "Other");
	}

	private final static String[] charsets = {"ISO-8859-1", "GBK", "GB2312", "GB18030", "UTF-8", "UTF-16", "UTF-16BE", "UTF-16LE", "UTF-32", "UTF-32BE", "UTF-32LE", "US-ASCII", "BIG5"};

	public static void charsetsInfo(String location, String testCharset, String testDstCharset) throws UnsupportedTagException, InvalidDataException, IOException {
		System.out.println("System Charset: "+System.getProperty("file.encoding"));
		Mp3File mp3file = new Mp3File(new File(location));
		for(String charset:charsets) {
			StringBuilder byteSb = new StringBuilder();
			StringBuilder byteSbTest = new StringBuilder();
			StringBuilder hexSb = new StringBuilder();
			byte[] bb = mp3file.getId3v2Tag().getTitle().getBytes(charset);
			for(byte b:bb) {
				byteSb.append(b);
				byteSb.append(",");
				hexSb.append(Integer.toHexString(b&0xff).toUpperCase());
			}
			String content = new String(bb, testDstCharset);
			bb = new String(bb, testCharset).getBytes();
			for(byte b:bb) {
				byteSbTest.append(b);
				byteSbTest.append(",");
			}
			System.out.println(charset+"====="+hexSb.toString()+"====="+byteSb.substring(0, byteSb.length()-1)+"====="+testCharset+": "+byteSbTest.substring(0, byteSbTest.length()-1)+"====="+content);
		}
	}
}
