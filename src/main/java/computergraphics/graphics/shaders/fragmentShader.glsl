#version 150 core
in vec2 pass_uv;
out vec4 fragColor;
uniform sampler2D textureSampler;
void main() {
    fragColor = texture(textureSampler, pass_uv);
}