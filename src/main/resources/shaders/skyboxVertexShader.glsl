#version 330 core

in vec3 position;
in vec2 uv;
out vec2 pass_uv;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main()
{
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
    pass_uv = uv;
}