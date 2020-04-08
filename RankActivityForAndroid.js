import React from 'react';
import{
	View,
	SafeAreaView,
	Text,
	TouchableOpacity,
	TextInput,
	StyleSheet,
	Dimensions, 
	FlatList,
	Image,
	RefreshControl,
}from 'react-native';    
import {fireBase} from './FireBaseConFig.js';
const screenWidth = Math.round(Dimensions.get('window').width);
const screenHeight= Math.round(Dimensions.get('window').height);   
const arrayPic = [
	{image: require('./src/n31.png')},
	{image: require('./src/n22.png')},
	{image: require('./src/n23.png')},
	{image: require('./src/n14.png')},
	{image: require('./src/n15.png')},
	{image: require('./src/n16.png')},
];
const arrayColor= ['#F09692', '#F8D588', '#F8D588', '#8DD78E', '#8DD78E', '#8DD78E'];
export default class RankActivityForAndroid extends React.Component{
	constructor(props) {
	  super(props); 
	  this.state = {
	  	text:'',
	  	nick: this.props.nickName,
	  	bezt: parseInt(this.props.bestScore),
	  	oldBezt:0,
	  	myRank:'6+',
	  };  
	} 
	UNSAFE_componentWillMount(){   
		this.updateScore(this.state.bezt); 
		this.getArray();
    }  
	componentDidMount(){    
		let delay = setTimeout(()=>{
			this.getArray(); 
		},3000);      
	}   
	pushScore = (bezt)=>{
		var date = new Date().getDate();
		var month= new Date().getMonth()+1;
		var year = new Date().getYear();
		var hour = new Date().getHours();
		var min = new Date().getMinutes(); 
		fireBase.database().ref('User').child(this.state.nick).set({
			score: bezt,
			mark: ((bezt==0)?9999999:-bezt)*100000000000+((((year*100+month)*100+date)*100+hour)*100+min),
		}); 
	}
	updateScore = () =>{   
		this.pushScore(this.state.bezt);
	}   
	getArray=()=>{
		let arr=[]; 
		var count=0;
		this.setState({myRank:'6+'});
		fireBase.database().ref('User').orderByChild('mark').limitToFirst(6).on('child_added', (keySnapshoot)=>{
	        arr.push({
	        	rank: ++count,
	          	nickName: keySnapshoot.key,
	          	score:keySnapshoot.val().score,  
	        });     
			this.setState({array: arr});
			if (keySnapshoot.key==this.state.nick) this.setState({myRank: ''+count});
		});  
	} 
	render(){	  
		return(
			<View style ={container('#fff')}> 
				<View style = {css.contianerTop}>
					<Text style = {css.title}>Ranking</Text>
				</View>
				<View
					style = {css.coverContainerBetween}>
					<View style = {[css.containerBetween]}>     
						 <FlatList
			                data = {this.state.array}
			                renderItem={({ item }) => <Items item={item} />}
			                keyExtractor={(item, index) => item.nickName}  
			            />
					</View>
				</View> 
				<View style={css._List}>
			    	<View style={[css._apartOfList]}>
			    		<Text style= {css._textRank}>{this.state.myRank}</Text>
			    	</View>

			    	<View>
			      		<Text style={css._textList} >{this.state.nick}</Text>
			      		<Text style={css._textList2} >{this.state.bezt}</Text> 
			      	</View>
			    </View>
				<View style = {css.containerBottom}> 
					<View style={{
						flexDirection:'row',  
						justifyContent: 'space-evenly',  
						paddingTop:20,
					}}>
						<TouchableOpacity
							onPress = {this.updateScore}
						>
							<Image source = {require('./src/upload.png')}
									style = {{
	                                	width:screenHeight/10,
	                                	height:screenHeight/10,
	                           		}}/>
						</TouchableOpacity>   
						<TouchableOpacity
							onPress = {this.getArray} 
						>
							<Image source = {require('./src/refresh.png')}
									style = {{
	                                	width:screenHeight/10,
	                                	height:screenHeight/10, 
	                           		}}/>
						</TouchableOpacity> 
					</View> 
					
				</View> 
			</View>
		);
				 
	}
}

Items = ({item})=>{
  return (
    <View style={css.List}>
    	<View> 
    		<Image source = {arrayPic[(item.rank)-1].image}
					style = {css.apartOfList}/>
    	</View>

    	<View style={css.apartOfList2}>
      		<Text style={textList(arrayColor[parseInt(item.rank)-1])} >{item.nickName}</Text>
      		<Text style={textList2(arrayColor[parseInt(item.rank)-1])} >{item.score}</Text> 
      	</View> 
    </View>
  );
}
const container=(color)=>{
	return{
		flex:1,
		flexDirection: 'column', 
		backgroundColor: color,
	}
}
const textList=(color)=>{
	return{
		fontFamily: 'math_tapping', 
	    fontSize: screenWidth/16,
	    color: color, 
	}
}
const textList2=(color)=>{
	return{
	    fontSize: screenWidth/18,
	    color: color,
	}
}

const css = StyleSheet.create({
	title:{
		fontFamily: 'math_tapping', 
	    fontSize: screenWidth/6,
	    color:'#2f2f2f', 
	},  
	List:{
		width: screenWidth*9/10,     
		flexDirection: 'row',    
		paddingBottom: 7,

	},
	apartOfList:{
		justifyContent: 'center',
		width: screenWidth/6,
		height: screenWidth/6,
		borderWidth: 0.3, 
	    borderRadius:10,
		borderColor: '#cecece',
		justifyContent: 'center',
		alignItems: 'center',
	},  
	apartOfList2:{ 
		paddingLeft: screenWidth*0.01,
	},  
	_List:{
		width: screenWidth*9/10,     
		flexDirection: 'row',    
		paddingLeft: screenWidth*0.03+7,
		paddingTop: 7,
	},
	_apartOfList:{
		justifyContent: 'center',
		width: screenWidth/6,
		height: screenWidth/6,
		borderWidth: 0.3, 
	    borderRadius:10,
		borderColor: '#000',
		justifyContent: 'center',
		alignItems: 'center', 
	},
	_textList:{
		fontFamily: 'math_tapping', 
	    fontSize: screenWidth/16,
	    color:'#000',
	},  
	_textList2:{ 
	    fontSize: screenWidth/18,
	    color:'#000',
	},
	_textRank:{
		fontFamily: 'math_tapping', 
	    fontSize: screenWidth/10,
	    color:'#000',
	}, 
	contianerTop:{
		flex:0.2, 
		flexDirection: 'column',
		justifyContent: 'flex-end',
		alignItems: 'center', 

	},
	coverContainerBetween:{
		flex:0.6, 
		paddingLeft: screenWidth*0.03,
		paddingRight: screenWidth*0.03,   

	},
	containerBetween:{ 
		borderRadius: 10,   
		backgroundColor: '#2f2f2f', 
		padding:10,  
	},

	containerBottom:{ 
		flex:0.2, 
		flexDirection: 'column'
	},
	showYourScore:{  
		paddingLeft: 10,
	}, 
	text:{
		fontFamily : 'math_tapping',
	    fontSize: screenWidth*0.077777,
	    color:'#000'
	},
	
});
