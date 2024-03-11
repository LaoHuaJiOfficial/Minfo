#define HIGHP

#define ALPHA 0.18
#define step 2.0
#define dashLength 10.0  // 虚线的长度

uniform sampler2D u_texture;
uniform vec2 u_texsize;
uniform vec2 u_invsize;
uniform float u_time;
uniform float u_dp;
uniform vec2 u_offset;

varying vec2 v_texCoords;

void main(){
    vec2 T = v_texCoords.xy;
    vec2 coords = (T * u_texsize) + u_offset;

    vec4 color = texture2D(u_texture, T);
    vec2 v = u_invsize;

    vec4 maxed = max(max(max(texture2D(u_texture, T + vec2(0, step) * v), texture2D(u_texture, T + vec2(0, -step) * v)), texture2D(u_texture, T + vec2(step, 0) * v)), texture2D(u_texture, T + vec2(-step, 0) * v));

    // Calculate dashed line effect
    float dash = mod(coords.y + coords.x + u_time * 0.33, dashLength * 1.5);

    // Determine if we're drawing the dashed line
    if(texture2D(u_texture, T).a < 0.9 && maxed.a > 0.9 && dash < dashLength){
        gl_FragColor = vec4(maxed.rgb, maxed.a * 100.0);
    }else{
        if(color.a > 0.0){
            color.a = ALPHA;
        }
        gl_FragColor = color;
    }
}
