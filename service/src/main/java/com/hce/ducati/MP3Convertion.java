package com.hce.ducati;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class MP3Convertion {

	public static void main(String[] args) throws IOException, UnsupportedTagException, InvalidDataException, NotSupportedException {
		/*File[] files = new File(SRC_DIR_LOCATION).listFiles();
		for(File file:files) {
			if(file.isDirectory())
				System.out.println("String dirName = \""+file.getName()+"\";");
		}*/
		String dirName = "孙佳星";
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
//		String dirName = "经典老掉牙";
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

		tree(new File(SRC_DIR_LOCATION+"/"+dirName), "Children’s Music");
//		tree(new File(SRC_DIR_LOCATION));
		System.out.println("Total: "+count);
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

	private static void process(File file, String dstGenreDescription) throws IOException, UnsupportedTagException, InvalidDataException, NotSupportedException {
		Mp3File mp3file = new Mp3File(file);
		ID3v2 id3v2Tag = mp3file.getId3v2Tag();
		if(id3v2Tag!=null) {
			String title = trim(id3v2Tag.getTitle());
			String artist = trim(id3v2Tag.getArtist());
			String album = trim(id3v2Tag.getAlbum());
			String albumArtist = trim(id3v2Tag.getAlbumArtist());
			String composer = trim(id3v2Tag.getComposer());
			String comment = trim(id3v2Tag.getComment());
			String genreDescription = trim(id3v2Tag.getGenreDescription());
			if(title!=null) {
				title = convert(id3v2Tag.getTitle());
				boolean sameCharset = sameCharset(id3v2Tag.getTitle(), title);
				String toPrint = "Title: "+title+"---"+sameCharset;
				if(sameCharset)
					System.err.println(toPrint);
				else
					System.out.println(toPrint);
				id3v2Tag.setTitle(title);
			}
			if(artist!=null) {
				artist = convert(artist);
				System.out.println("Artist: "+artist);
				id3v2Tag.setArtist(artist);
			}
			if(album!=null) {
				album = convert(album);
				System.out.println("Album: "+album);
				id3v2Tag.setAlbum(album);
			}
			if(albumArtist!=null) {
				albumArtist = convert(albumArtist);
				System.out.println("AlbumArtist: "+albumArtist);
				id3v2Tag.setAlbumArtist(albumArtist);
			}
			if(composer!=null) {
				composer = convert(composer);
				System.out.println("Composer: "+composer);
				id3v2Tag.setComposer(composer);
			}
			if(comment!=null) {
				comment = convert(comment);
				System.out.println("Comment: "+comment);
				id3v2Tag.setComment(comment);
			}
			System.out.println("Genre: "+id3v2Tag.getGenre());
			if(dstGenreDescription!=null) {
				id3v2Tag.setGenreDescription(dstGenreDescription);
			} else {
				if(genreDescription!=null) {
					genreDescription = convert(genreDescription);
					System.out.println("GenreDescription: "+genreDescription);
					try {
						id3v2Tag.setGenreDescription(genreDescription);
					} catch(IllegalArgumentException e) {
						genreDescription = genreDescriptionMap.get(genreDescription);
						id3v2Tag.setGenreDescription(genreDescription);
					}
				}
			}
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
//			System.out.println("AbsolutePath: "+file.getAbsolutePath());
		} else
			System.err.println("NO_ID3V2TAG----------"+file.getAbsolutePath());
		System.out.println("-------------------");
		
		ID3v1 id3v1Tag = mp3file.getId3v1Tag();
		if(id3v1Tag!=null) {
			String title = trim(id3v1Tag.getTitle());
			String artist = trim(id3v1Tag.getArtist());
			String album = trim(id3v1Tag.getAlbum());
			String comment = trim(id3v1Tag.getComment());
			if(title!=null) {
				title = convert(title);
				System.out.println("Title: "+title);
				id3v1Tag.setTitle(title);
			}
			if(artist!=null) {
				artist = convert(artist);
				System.out.println("Artist: "+artist);
				id3v1Tag.setArtist(artist);
			}
			if(album!=null) {
				album = convert(album);
				System.out.println("Album: "+album);
				id3v1Tag.setAlbum(album);
			}
			if(comment!=null) {
				comment = convert(comment);
				System.out.println("Comment: "+comment);
				id3v1Tag.setComment(comment);
			}
			System.out.println("Genre: "+id3v1Tag.getGenre());
//			System.out.println("Track: "+id3v1Tag.getTrack());
//			System.out.println("Version: "+id3v1Tag.getVersion());
//			System.out.println("Year: "+id3v1Tag.getYear());
		} else
			System.err.println("NO_ID3V1TAG----------"+file.getAbsolutePath());
//		System.out.println("encoding: "+System.getProperty("file.encoding"));
		String newLocation = file.getAbsolutePath().replaceAll(SRC_DIR_LOCATION, DST_DIR_LOCATION);
		System.out.println("New Location: "+newLocation);
		System.out.println("=================================");
		mp3file.save(newLocation);
	}

	private static String convert(String s) throws UnsupportedEncodingException {
		return new String(s.getBytes("ISO-8859-1"), "GBK");
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
//		genreDescriptionMap.put("", "Other");
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

	private static boolean sameCharset(String src, String dst) {
		byte[] bbSrc = src.getBytes();
		byte[] bbDst = dst.getBytes();
		if(bbSrc.length!=bbDst.length)
			return false;
		for(int i=0;i<bbSrc.length;i++) {
			if(bbSrc[i]!=bbDst[i])
				return false;
		}
		return true;
	}

	private final static String[] charsets = {"ISO-8859-1", "GBK", "GB2312", "GB18030", "UTF-8", "UTF-16", "UTF-16BE", "UTF-16LE", "UTF-32", "UTF-32BE", "UTF-32LE", "US-ASCII"};

	private static void print(String s, String toCharset) throws UnsupportedEncodingException {
		for(String charset:charsets) {
			StringBuilder sb = new StringBuilder();
			byte[] b = s.getBytes(charset);
			for(byte bb:b) {
				sb.append(Integer.toHexString(bb&0xff).toUpperCase());
			}
			System.out.println(charset+"======"+sb.toString()+"======"+new String(b, toCharset==null?charset:toCharset));
		}
		System.out.println("------------------");
	}
}
