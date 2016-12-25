package io.github.rkluszczynski.avro.cli;

import com.beust.jcommander.Parameter;

class CliMainParameters {
    @Parameter(
            names = {"--help", "-h"},
            description = "Show help",
            help = true
    )
    private boolean help = false;

    boolean isHelp() {
        return help;
    }
}
