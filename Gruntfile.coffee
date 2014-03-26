module.exports = (grunt) ->
  grunt.initConfig
    protractor:
      e2e:
        options:
          configFile: 'protractor.conf.js'

    watch:
      app:
        files: ['app/**/*', 'src/**/*']
        tasks: ['protractor']

  grunt.loadNpmTasks 'grunt-protractor-runner'
  grunt.loadNpmTasks 'grunt-contrib-watch'
  grunt.registerTask 'test', ['protractor']
  grunt.registerTask 'default', ['test', 'watch']
