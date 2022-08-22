const path = require('path');

module.exports = {
	entry: './src/main/js/entryPoint.js',
	devtool: 'source-map',
	cache: true,
	mode: 'development',
	output: {
		path: path.resolve(__dirname, 'src/main/resources/static/built'),
		//path: __dirname,
		filename: 'bundle.js'
	},

	module: {
		rules: [
			{
				test: path.join(__dirname, '.'),
				exclude: /(node_modules)/,
				use: [{
					loader: 'babel-loader',
					options: {
						presets: ["@babel/preset-env", "@babel/preset-react"]
					}
				}]
			},
			/*{
				test: /(@?react-(navigation|native)).*\.(ts|js)x?$/,
				include: /node_modules/,
				exclude: [/react-native-web/, /\.(native|ios|android)\.(ts|js)x?$/],
				loader: 'babel-loader',
				options: {
					presets: ["@babel/preset-env", "@babel/preset-react"]
				}
			},*/
			{
				test: /\.css$/i,
				use: ["style-loader", "css-loader"],
			},
			// This would match ui-kitten
			/*{
				test: /@?(datepicker).*\.(ts|js)x?$/,
				loader: 'babel-loader',
				options: {
					presets: ["@babel/preset-env", "@babel/preset-react"]
				}
			}*/
		]
	},
	resolve: {
		extensions: ['', '.js', '.jsx', '.css']
	},
};