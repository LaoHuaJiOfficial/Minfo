#define HIGHP

#define ALPHA 0.18
#define step 2.0

uniform sampler2D u_texture;

varying vec2 v_texCoords;

void main(){
    vec2 T = v_texCoords.xy;

    vec4 color = texture2D(u_texture, T);

    if(color.a < 0.9){
        gl_FragColor = vec4(0.6, 0.6, 0.6, 0.6);
    } else {
        if(color.a > 0.0){
            color.a = ALPHA;
        }
        gl_FragColor = color;
    }
}
