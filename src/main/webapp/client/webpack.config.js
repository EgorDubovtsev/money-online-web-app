const path = require('path')
const webpack = require('webpack')

// const src = path.resolve(__dirname, 'src')

const options = {
  enableOptimization: false
}

module.exports = env => {
  const getEnvVar = v => {
    return env ? env[v] : undefined
  }

  const getEnvVarStr = v => {
    return env ? JSON.stringify(env[v]) : undefined
  }

  const base = {
    entry: {
      index: './js/index.js'
    },
    output: {
      path: __dirname,
      filename: './built/[name].bundle.js',
    },
    module: {
      rules: [
        {
          test: /\.js$/,
          exclude: /node_modules/,
          loader: ['babel-loader', 'eslint-loader']
        },
        {
          test: /\.css$/,
          use: ['style-loader', 'css-loader']
        }
      ]
    },
    plugins: [
      new webpack.DefinePlugin({
        LOCAL: false
      })
    ]
  }

  const dev = {
    devServer: {
      contentBase: path.resolve(__dirname, 'html'),
      publicPath: '/',
      compress: true,
      port: 2992,
      hot: true,
      inline: true
    },
    plugins: [
      new webpack.HotModuleReplacementPlugin({
        LOCAL: getEnvVar('local'),
        USERNAME: getEnvVarStr('user'),
        PASSWORD: getEnvVarStr('password')
      })
    ]
  }

  const prod = {}

  //  ? react-loadable
  const optimization = {
  }

  let conf = env && env.local ? { ...base, ...dev } : { ...base, ...prod }

  conf = options.enableOptimization ? { ...conf, ...optimization } : conf

  return conf
}
