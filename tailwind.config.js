module.exports = {
  important: true,
  safelist: [
    // 'text-xl',
    // 'text-center'
  ],
  // mode: 'jit',
  content: [
    './src/core/**/*.{clj,cljs,cljc}',
    './src/web/**/*.{clj,cljs,cljc}'
  ],
  // content: process.env.NODE_ENV === 'production'
  //   ? [
  //     './volume/web/public/*.js',
  //     './volume/web/public/**/*.js'
  //   ]
  //   : [
  //     './volume/web/public/main.js',
  //     './volume/web/public/manager.js',
  //     './volume/web/public/cljs-runtime/*.js'
  //   ],
  darkMode: ['class'],
  theme: {
    extend: {
      spacing: {
	'88': '22rem',
	'128': '32rem'
      }
    },
  },
  variants: {
    extend: {},
  },
  plugins: []
};
