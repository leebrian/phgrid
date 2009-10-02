package gov.cdc.ncphi.phgrid.polygon.gmaps;

import it.rambow.master.javautils.PolylineEncoder;
import it.rambow.master.javautils.Track;
import it.rambow.master.javautils.Trackpoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class GoogleMapEncodingStringGenerator {
	
	private static final Logger logger = Logger.getLogger(GoogleMapEncodingStringGenerator.class);
	private static GoogleMapEncodingStringGenerator gmsg;
	private static Map<Integer, String> pointsMap, levelsMap;
	private static final String POINTSKEY = "encodedPoints", LEVELSKEY = "encodedLevels", LINEWEIGHT = "1";
	private static final int	ZOOMFACTOR = 2, NUMLEVELS = 18;
	private static final double VERYSMALL = 0.00001;
	private static final boolean FORCENDPOINTS = true;
	private static PolylineEncoder polencode;
	
	
	private GoogleMapEncodingStringGenerator()
	{
		pointsMap = new HashMap<Integer,String>();
		levelsMap = new HashMap<Integer,String>();
		polencode = new PolylineEncoder(NUMLEVELS, ZOOMFACTOR,VERYSMALL, FORCENDPOINTS );
		
	}
	/**
	 * Returns a google map polygon string with an encoded polygon.
	 * @param polygon
	 * @param position
	 * @param pcproc
	 * @param polyproc
	 * @return
	 */
	public String getPolygonString(GmapPolygon polygon, String position, PolygonColorProcessor pcproc, PolygonPopupHandler polyproc, String passInKey, int collectionIndex)
	{
		String returnable = "";
		
     		List<Coordinate> coorlist =  polygon.getCoordinates();
      		

      		
     		String pointString = "";
     		String levelsString = "";
     		if (pointsMap.containsKey(new Integer(coorlist.hashCode())) && levelsMap.containsKey(new Integer(coorlist.hashCode())))
     		{
     			pointString = pointsMap.get(new Integer(coorlist.hashCode()));
     			levelsString = levelsMap.get(new Integer(coorlist.hashCode()));
     		}
     		else
     		{
     			Coordinate coor;
     			Iterator<Coordinate> coorEnum = coorlist.iterator();
     			Track trk = new Track();
     			
     			while(coorEnum.hasNext())
     			{
     				coor = coorEnum.next();
     				trk.addTrackpoint(new Trackpoint(new Double(coor.getLatitude()), new Double(coor.getLongitude())));
     				//coorString += "new GLatLng(" + coor.getLatitude() + ", " + coor.getLongitude() + "),\n";
     			}  
     			HashMap<String, String> resultMap  = polencode.dpEncode(trk);
     			pointString = resultMap.get(POINTSKEY);
     			levelsString = resultMap.get(LEVELSKEY);
     			/*if (pointString.substring(pointString.length()-1, pointString.length()).equals("\\"))
     			{
     				pointString += "\\";
     			} */
     			pointsMap.put(new Integer(coorlist.hashCode()), pointString);
     			levelsMap.put(new Integer(coorlist.hashCode()), levelsString);
     		}
     		//returnable += coorString;
     		ColorBean cb = pcproc.getColor(polygon);
      		/*
      		 * var polygon1_1 = new GPolygon.fromEncoded({
 				polylines: [{color: "#0000ff",
 weight: 4,
 opacity: 0.8,
 points: "kfebE|fffO~hMvxGjgIr~Bd{L_X~]wGpfH_|AtnCdT`wCum@rhKocI|`LofB`xEEhoGrrCl|Eu]|iMzdDbyEs`@xuDkGxdEjiCftG_|@l{Dly@lyC}z@p|@kyCx}Eg~DhbHubCxkNe_Br@dv}A|n@tniB{Jhf\\`Vfpf@_h@bb}@SlcN_^l}iAfBr`tAtlHmu@bcN`_FxqDg|@ffKsmMngFq~AxiGm~KdpAw`G~yCq}BnjHo`@nlKzrDz{CppCpuH{dDjaGwsA~vNj}J|{F`tVf~Fnc@~tG`mf@`Adng@}rFknj@mfDgkCuxVfo[i`f@~|@y_W~wS~nnAjsUsbL~`c@bbAxyNc~aA|aBorr@tiAcnVp`@cn|@ndBmas@hxAmmc@bp@_eaAe{Ee|NqiAi~s@wfEwfbAe}GcgKst@wzy@owE}xo@_|Doyf@}cDox|@u{GwbEsSssl@ieEaqZgeBcoUy{Aqp|@c~G}YbrB{|DztEknM`}Hyw@`EqNqxg@ha@c{hA~RqejAyB{}At{@oahAhFa~HfJgtzAe@wz@fRawsAzLy{q@~}W{`D~ym@gmHfkFkv@l`KkvAhnl@iaIfuf@gpGjyWeaC`sIghAp|o@qoH`b_@m|DnsKudA~|x@{uJzw@cA~no@}hIhqKarBx|D{tEv`Cr_@piAss@n}@{cAptGie@juA{jB~{DhrAblBglB`~CsL|fCqrApdKotMpoKoc@r}CuwC~jEza@v{@nsAl`BcoAtwCb}DflBhSzbFsmOnwCmdDvz@uBb{@|eApLlqBttB}n@baAhlAxb@`gH~}B__BppAngHvmAnfBvvFxsDj~Eqk@xcCl`AtyBn`@~jEg`@ddFprA",
 levels: "PHKHBHHJIKHIHIFIIHLHHJHOGFGIFEGNIILIIHHLJHKGKNJKJNIMKKNMJPCAFCFLHFFCGEIFGEFEGLHIGPGFEGBHCECPAGCDEHGEFDHFFLHIIFIHIIHJHJIKHIIHKHMHHIIHJJIGKIFHHP",
 zoomFactor: 2,
 numLevels: 18}],
 fill:true,
 color:"#0000ff",
 opacity: 0.4,
 outline: true
});
      		 */
     		returnable = "var polygon"+getPosition(position,collectionIndex)+" = new GPolygon.fromEncoded({\n";
     		returnable +="polylines: [{color: \"" + cb.getBorderColor() + "\",\n"; 
     		returnable +="weight: " + LINEWEIGHT +",\n"; 
     		returnable +="points: \"" + pointString + "\",\n";
     		returnable +="levels: \"" + levelsString + "\",\n";
     		returnable +="zoomFactor: " + ZOOMFACTOR + ",\n";
     		returnable +="numLevels: " +NUMLEVELS + "}],\n";
     		returnable +="fill:true,\n";
     		returnable +="color:\""+cb.getFillColor() + "\",\n";
       		returnable +="opacity: " + cb.getFillStrength() +",\n"; 
       		returnable +="outline: true \n";
     		returnable +="});"; 
       	   returnable += "GEvent.addListener(polygon" + getPosition(position,collectionIndex) + ", \"click\", function(latlng) {\n";
       	   returnable += "map.openInfoWindowHtml(latlng, ";
     		returnable += polyproc.getPopupString(position, polygon, passInKey, collectionIndex);
        	   returnable += ")\n});\n";
        	   returnable += "map.addOverlay(polygon"+getPosition(position,collectionIndex)+");\n";
     		logger.debug("polygon text: " + returnable);
     		return returnable;
		
	}
	
	public static GoogleMapEncodingStringGenerator getStringGenerator()
	{
		if (gmsg == null)
		{
			gmsg = new GoogleMapEncodingStringGenerator();
		}
		return gmsg;
	}
	
	public String getPosition(String position, int index )
	{
		String returnable = "";
		returnable = index + "i" + position;
		return returnable;
	}


}
